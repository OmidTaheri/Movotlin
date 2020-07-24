package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application

import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity

import ir.omidtaheri.domain.interactor.GetMovieDetail

import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper


class DetailViewModel(
    val GetDetailMovieUseCase: GetMovieDetail,
    val UiDomainMapper: MovieDetailEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel<MovieDetailUiEntity>(application) {


    fun getMovieDetail(id: Long) {
        _isLoading.value = true
        val disposable = GetDetailMovieUseCase.execute(id).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = UiDomainMapper.mapToUiEntity(response.data!!)
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

    private fun HandleSnackBarError(errorDataState: DataState.ERROR<MovieDetailDomainEntity>) {
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

    private fun HandleToastError(errorDataState: DataState.ERROR<MovieDetailDomainEntity>) {
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