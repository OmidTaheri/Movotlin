package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams
import ir.omidtaheri.search.entity.MultiMovieUiEntity
import ir.omidtaheri.search.mapper.MultiMovieEntityUiDomainMapper

class SearchViewModel(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) : BaseViewModel(application) {


    private val _DataLive: MutableLiveData<MultiMovieUiEntity>
    val DataLive: LiveData<MultiMovieUiEntity>
        get() = _DataLive

    private val _SearchErrorState: MutableLiveData<Boolean>
    val SearchErrorState: LiveData<Boolean>
        get() = _SearchErrorState


    init {
        _DataLive = MutableLiveData()
        _SearchErrorState = MutableLiveData()
    }


    fun SearchMovieByQuery(query: String, page: Int) {
        //_isLoading.value = true
        val searchParams = SearchMovieByQueryParams(query, page)
        val disposable = searchMoviesByQuery.execute(searchParams).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {
                  //  _isLoading.value = false
                    _DataLive.value = multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }


                is DataState.ERROR -> {
                    //_isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _SearchErrorState.value = true
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