package ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetPopularMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesWithoutPaging
import ir.omidtaheri.mainpagetv.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper

class MainViewModel(
    val getPopularMoviesUseCase: GetPopularMoviesWithoutPaging,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesWithoutPaging,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesWithoutPaging,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _poularLiveData: MutableLiveData<MultiMovieUiEntity>
    val poularLiveData: LiveData<MultiMovieUiEntity>
        get() = _poularLiveData

    private val _topRateLiveData: MutableLiveData<MultiMovieUiEntity>
    val topRateLiveData: LiveData<MultiMovieUiEntity>
        get() = _topRateLiveData

    private val _upComingLiveData: MutableLiveData<MultiMovieUiEntity>
    val upComingLiveData: LiveData<MultiMovieUiEntity>
        get() = _upComingLiveData

    init {
        _poularLiveData = MutableLiveData()
        _topRateLiveData = MutableLiveData()
        _upComingLiveData = MutableLiveData()
    }

    fun getPopularMovieList() {

        val disposable = getPopularMoviesUseCase.execute(1).subscribe { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _poularLiveData.value =
                        multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getTopRatedMovieList() {
        // _isTopRateLoading.value = true
        val disposable = getTopRatedMoviesUseCase.execute(1).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {

                    _topRateLiveData.value =
                        multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getUpComingMovieList() {
        // _isUpComingLoading.value = true
        val disposable = getUpcomingMoviesUseCase.execute(1).subscribeBy {

                response ->
            when (response) {
                is DataState.SUCCESS -> {

                    _upComingLiveData.value
                    multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
