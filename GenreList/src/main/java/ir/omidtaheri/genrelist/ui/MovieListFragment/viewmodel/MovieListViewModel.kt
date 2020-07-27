package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

import android.app.Application
import io.reactivex.rxjava3.kotlin.subscribeBy

import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.entity.MultiMovieUiEntity
import ir.omidtaheri.genrelist.mapper.MultiMovieEntityUiDomainMapper


class MovieListViewModel(
    val getMovieListByGenreId: GetMovieListByGenreId,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel<MultiMovieUiEntity>(application) {


    fun getMovieListByGenre() {
        _isLoading.value = true
        val params = mapOf<String, Any>()
        val disposable = getMovieListByGenreId.execute(params).subscribeBy { response ->
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
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
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