package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper

class MainViewModel(
    val GetPopularMoviesUseCase: GetPopularMovies,
    val GetTopRatedMoviesUseCase: GetTopRatedMovies,
    val GetUpcomingMoviesUseCase: GetUpcomingMovies,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel<MultiMovieUiEntity>(application) {


    fun getPopularMovieList(page: Int) {
        _isLoading.value = true
        val disposable = GetPopularMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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




    fun getTopRatedMovieList(page: Int) {
        _isLoading.value = true
        val disposable = GetTopRatedMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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




    fun getUpComingMovieList(page: Int) {
        _isLoading.value = true
        val disposable = GetUpcomingMoviesUseCase.execute(page).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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






    private fun HandleSnackBarError(errorDataState: DataState.ERROR<MultiMovieDomainEntity>) {
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

    private fun HandleToastError(errorDataState: DataState.ERROR<MultiMovieDomainEntity>) {
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