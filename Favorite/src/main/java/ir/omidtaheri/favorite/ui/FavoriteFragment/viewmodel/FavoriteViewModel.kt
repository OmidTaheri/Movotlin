package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetFavoriedMovieListByFlowable
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper

class FavoriteViewModel(
    private val getFavoriedMovieListByFlowable: GetFavoriedMovieListByFlowable,
    private val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mapplication: Application
) :
    BaseAndroidViewModel(mapplication, state) {

    private val _dataLive: MutableLiveData<List<FavoritedMovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<List<FavoritedMovieUiEntity>>
        get() = _dataLive

    private val _favoriteErrorState: MutableLiveData<Boolean> = MutableLiveData()
    val favoriteErrorState: LiveData<Boolean>
        get() = _favoriteErrorState

    fun getFavoritedMovieListByFlowable() {
        val disposable = getFavoriedMovieListByFlowable.execute(Unit).subscribeBy { response ->
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

        addDisposable(disposable)
    }


    private fun handleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _errorSnackBar.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorSnackBar.value =
                    mapplication.applicationContext.getString(
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
                    mapplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }
}
