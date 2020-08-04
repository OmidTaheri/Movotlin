package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.favorite.entity.FavoritedMovieUiEntity
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper

class FavoriteViewModel(
    val getFavoriedMovieList: GetFavoriedMovieList,
    val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {


    private val _DataLive: MutableLiveData<List<FavoritedMovieUiEntity>>
    val DataLive: LiveData<List<FavoritedMovieUiEntity>>
        get() = _DataLive

    private val _FavoriteErrorState: MutableLiveData<Boolean>
    val FavoriteErrorState: LiveData<Boolean>
        get() = _FavoriteErrorState


    init {
        _DataLive = MutableLiveData()
        _FavoriteErrorState = MutableLiveData()
    }


    fun getFavoritedMovieList() {
        // _isLoading.value = true
        val disposable = getFavoriedMovieList.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    //_isLoading.value = false
                    _DataLive.value = response.data?.map {
                        favoritedMovieEntityUiDomainMapper.mapToUiEntity(it)
                    }
                }


                is DataState.ERROR -> {
                    // _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _FavoriteErrorState.value = true
                            }
                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }

    private fun HandleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorSnackBar.value =
                    messageHolder.Message
                is MessageHolder.Res -> _ErrorSnackBar.value =
                    ApplicationClass.getString(
                        messageHolder.ResId
                    )


            }

        }
    }

    private fun HandleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorToast.value =
                    messageHolder.Message
                is MessageHolder.Res -> _ErrorToast.value =
                    ApplicationClass.getString(
                        messageHolder.ResId
                    )


            }

        }
    }
}