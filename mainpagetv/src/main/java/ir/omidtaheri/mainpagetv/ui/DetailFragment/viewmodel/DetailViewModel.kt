package ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
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

class DetailViewModel(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMoviesWithoutPaging,
    val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseAndroidViewModel(application) {

    private val _detailLiveData: MutableLiveData<MovieDetailUiEntity>
    val detailLiveData: LiveData<MovieDetailUiEntity>
        get() = _detailLiveData

//    private val _imageListLiveData: MutableLiveData<MovieImageUiEntity>
//    val imageListLiveData: LiveData<MovieImageUiEntity>
//        get() = _imageListLiveData
//
//    private val _videoListLiveData: MutableLiveData<MovieVideoUiEntity>
//    val videoListLiveData: LiveData<MovieVideoUiEntity>
//        get() = _videoListLiveData

    private val _similarMoviesLiveData: MutableLiveData<MultiMovieUiEntity>
    val similarMoviesLiveData: LiveData<MultiMovieUiEntity>
        get() = _similarMoviesLiveData

//    private val _isImageLoading: MutableLiveData<Boolean>
//    val isImageLoading: LiveData<Boolean>
//        get() = _isImageLoading

//    private val _isVideoLoading: MutableLiveData<Boolean>
//    val isVideoLoading: LiveData<Boolean>
//        get() = _isVideoLoading

//    private val _imagesErrorState: MutableLiveData<Boolean>
//    val imagesErrorState: LiveData<Boolean>
//        get() = _imagesErrorState
//
//    private val _videoErrorState: MutableLiveData<Boolean>
//    val videoErrorState: LiveData<Boolean>
//        get() = _videoErrorState

    init {
//        _imageListLiveData = MutableLiveData()
//        _videoListLiveData = MutableLiveData()
        _similarMoviesLiveData = MutableLiveData()
        _detailLiveData = MutableLiveData()

//        _videoErrorState = MutableLiveData()
//        _imagesErrorState = MutableLiveData()
//
//        _isVideoLoading = MutableLiveData()
//        _isImageLoading = MutableLiveData()
    }

    fun getSimilarMovies(movieId: Int) {
        val params = GetSimilarMoviesParams(movieId, 1)

        val disposable = getSimilarMovies.execute(params).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _similarMoviesLiveData.value =
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
        // _isLoading.value = true
        val disposable = getDetailMovieUseCase.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _detailLiveData.value =
                        movieDetailEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
