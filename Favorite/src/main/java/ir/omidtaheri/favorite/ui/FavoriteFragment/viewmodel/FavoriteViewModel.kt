package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriedMovieList: GetFavoriedMovieList,
    private val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private var recyclerViewState: LinearLayoutManager.SavedState? = null

    private val _dataLive: MutableLiveData<List<FavoritedMovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<List<FavoritedMovieUiEntity>>
        get() = _dataLive

    private val _favoriteErrorState: MutableLiveData<Boolean> = MutableLiveData()
    val favoriteErrorState: LiveData<Boolean>
        get() = _favoriteErrorState

    fun getFavoritedMovieList() {
        viewModelScope.launch {
            getFavoriedMovieList.execute(Unit).collect { response ->
                when (response) {

                    is DataState.SUCCESS -> {
                        _dataLive.value = response.data?.map {
                            favoritedMovieEntityUiDomainMapper.mapToUiEntity(it)
                        }
                    }

                    is DataState.ERROR -> {
                        response.let { errorDataState ->

                            when (errorDataState.stateMessage?.uiComponentType) {
                                is UiComponentType.SNACKBAR -> {
                                    handleSnackBarError(errorDataState as DataState.ERROR<Any>)
                                }

                                is UiComponentType.TOAST -> {
                                    handleToastError(errorDataState as DataState.ERROR<Any>)
                                }

                                is UiComponentType.DIALOG -> {
                                    _favoriteErrorState.value = true
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    private fun handleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _errorSnackBar.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorSnackBar.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _errorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorToast.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    fun saveFragmentState(
        layoutManagerState: LinearLayoutManager.SavedState?
    ) {
        recyclerViewState = layoutManagerState
    }

    fun restoreStateOfRecyclerView(): LinearLayoutManager.SavedState? {
        return recyclerViewState
    }

}
