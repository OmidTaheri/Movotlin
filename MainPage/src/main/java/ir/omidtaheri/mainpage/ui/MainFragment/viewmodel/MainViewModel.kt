package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

class MainViewModel(
    val GetMoviesUseCase: GetMovies,
    val UiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel<List<MovieUiEntity>>(application) {


    fun getMovieList() {

        _isLoading.value = true
        val disposable = GetMoviesUseCase.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = response.data?.map {
                        UiDomainMapper.mapToUiEntity(it)
                    }
                }


                is DataState.ERROR -> {
                    _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState)
                            }
                        }


                    }


                }

            }
        }

        addDisposable(disposable)
    }

    private fun HandleSnackBarError(errorDataState: DataState.ERROR<List<MovieDomainEntity>>) {
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

    private fun HandleToastError(errorDataState: DataState.ERROR<List<MovieDomainEntity>>) {
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