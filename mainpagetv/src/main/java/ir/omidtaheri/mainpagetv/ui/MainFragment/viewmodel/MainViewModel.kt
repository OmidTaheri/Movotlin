package ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetPopularMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesWithoutPaging
import ir.omidtaheri.mainpagetv.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesWithoutPaging,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesWithoutPaging,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesWithoutPaging,
    private val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private val _poularLiveData: MutableLiveData<MultiMovieUiEntity> = MutableLiveData()
    val poularLiveData: LiveData<MultiMovieUiEntity>
        get() = _poularLiveData

    private val _topRateLiveData: MutableLiveData<MultiMovieUiEntity> = MutableLiveData()
    val topRateLiveData: LiveData<MultiMovieUiEntity>
        get() = _topRateLiveData

    private val _upComingLiveData: MutableLiveData<MultiMovieUiEntity> = MutableLiveData()
    val upComingLiveData: LiveData<MultiMovieUiEntity>
        get() = _upComingLiveData

    fun getPopularMovieList() {

        viewModelScope.launch {
            getPopularMoviesUseCase.execute(1).collectLatest { response ->
                when (response) {
                    is DataState.SUCCESS -> {
                        _poularLiveData.value =
                            multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
                            }
                        }
                    }
                }
            }

        }
    }


    fun getTopRatedMovieList() {
        viewModelScope.launch {
            getTopRatedMoviesUseCase.execute(1).collectLatest { response ->
                when (response) {
                    is DataState.SUCCESS -> {
                        _topRateLiveData.value =
                            multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
                            }
                        }
                    }
                }
            }

        }
    }

    fun getUpComingMovieList() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase.execute(1).collectLatest {

                    response ->
                when (response) {
                    is DataState.SUCCESS -> {

                        _upComingLiveData.value
                        multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
}
