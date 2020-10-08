package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.domain.interactor.GetFavoriedMovieListByFlowable
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper

class FavoriteViewModel(
    val getFavoriedMovieListByFlowable: GetFavoriedMovieListByFlowable,
    val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _dataLive: MutableLiveData<List<FavoritedMovieUiEntity>>
    val dataLive: LiveData<List<FavoritedMovieUiEntity>>
        get() = _dataLive

    private val _favoriteErrorState: MutableLiveData<Boolean>
    val favoriteErrorState: LiveData<Boolean>
        get() = _favoriteErrorState

    init {
        _dataLive = MutableLiveData()
        _favoriteErrorState = MutableLiveData()
    }

    fun getFavoritedMovieListByFlowable() {
        // _isLoading.value = true
        val disposable = getFavoriedMovieListByFlowable.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _dataLive.value = response.data?.map {
                        favoritedMovieEntityUiDomainMapper.mapToUiEntity(it)
                    }
                }

                is DataState.ERROR -> {
                    // _isLoading.value = false
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
                is MessageHolder.MESSAGE -> _ErrorSnackBar.value =
                    messageHolder.message
                is MessageHolder.Res -> _ErrorSnackBar.value =
                    ApplicationClass.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _ErrorToast.value =
                    ApplicationClass.getString(
                        messageHolder.resId
                    )
            }
        }
    }
}
