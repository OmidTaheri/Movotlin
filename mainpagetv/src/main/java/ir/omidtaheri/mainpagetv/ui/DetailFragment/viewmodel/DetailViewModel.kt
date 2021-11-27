package ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.mainpagetv.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpagetv.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpagetv.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailMovieUseCase: GetMovieDetail,
    private val getMovieImagesById: GetMovieImagesById,
    private val getMovieVideosById: GetMovieVideosById,
    private val getSimilarMovies: GetSimilarMoviesWithoutPaging,
    private val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    private val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    private val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    private val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private val _detailLiveData: MutableLiveData<MovieDetailUiEntity> = MutableLiveData()
    val detailLiveData: LiveData<MovieDetailUiEntity>
        get() = _detailLiveData


    private val _similarMoviesLiveData: MutableLiveData<MultiMovieUiEntity> = MutableLiveData()
    val similarMoviesLiveData: LiveData<MultiMovieUiEntity>
        get() = _similarMoviesLiveData


    fun getSimilarMovies(movieId: Int) {
        val params = GetSimilarMoviesParams(movieId, 1)

        viewModelScope.launch {
            getSimilarMovies.execute(params).collectLatest { response ->
                when (response) {
                    is DataState.SUCCESS -> {
                        _similarMoviesLiveData.value =
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


//    fun getMovieVideos(movieId: Int) {
//        // _isLoading.value = true
//        val disposable = getMovieVideosById.execute(movieId).subscribeBy { response ->
//            when (response) {
//                is DataState.SUCCESS -> {
//                    // _isLoading.value = false
//                    _videoListLiveData.value =
//                        movieVideoEntityUiDomainMapper.mapToUiEntity(response.data!!)
//                }
//
//                is DataState.ERROR -> {
//                    // _isLoading.value = false
//                    response.let { errorDataState ->
//
//                        when (errorDataState.stateMessage?.uiComponentType) {
//                            is UiComponentType.SNACKBAR -> {
//                                handleSnackBarError(errorDataState as DataState.ERROR<Any>)
//                            }
//
//                            is UiComponentType.TOAST -> {
//                                handleToastError(errorDataState as DataState.ERROR<Any>)
//                            }
//
//                            is UiComponentType.DIALOG -> {
//                                _videoErrorState.value = true
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        addDisposable(disposable)
//    }
//
//    fun getMovieImages(movieId: Int) {
//        // _isLoading.value = true
//        val disposable = getMovieImagesById.execute(movieId).subscribeBy { response ->
//            when (response) {
//                is DataState.SUCCESS -> {
//                    // _isLoading.value = false
//                    _imageListLiveData.value =
//                        movieImageEntityUiDomainMapper.mapToUiEntity(response.data!!)
//                }
//
//                is DataState.ERROR -> {
//                    // _isLoading.value = false
//                    response.let { errorDataState ->
//
//                        when (errorDataState.stateMessage?.uiComponentType) {
//                            is UiComponentType.SNACKBAR -> {
//                                handleSnackBarError(errorDataState as DataState.ERROR<Any>)
//                            }
//
//                            is UiComponentType.TOAST -> {
//                                handleToastError(errorDataState as DataState.ERROR<Any>)
//                            }
//
//                            is UiComponentType.DIALOG -> {
//                                _imagesErrorState.value = true
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        addDisposable(disposable)
//    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase.execute(movieId).collectLatest { response ->
                when (response) {
                    is DataState.SUCCESS -> {
                        _isLoading.value = false
                        _detailLiveData.value =
                            movieDetailEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
