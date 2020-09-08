package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.interactor.FavorieMovie
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesSinglePage
import ir.omidtaheri.domain.interactor.UnfavoriteMovie
import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpage.entity.MovieImageUiEntity
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.entity.MovieVideoUiEntity
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieVideoEntityUiDomainMapper

class DetailViewModel(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMoviesSinglePage,
    val favorieMovie: FavorieMovie,
    val unfavoriteMovie: UnfavoriteMovie,
    val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _detailLiveData: MutableLiveData<MovieDetailUiEntity>
    val detailLiveData: LiveData<MovieDetailUiEntity>
        get() = _detailLiveData

    private val _imageListLiveData: MutableLiveData<MovieImageUiEntity>
    val imageListLiveData: LiveData<MovieImageUiEntity>
        get() = _imageListLiveData

    private val _videoListLiveData: MutableLiveData<MovieVideoUiEntity>
    val videoListLiveData: LiveData<MovieVideoUiEntity>
        get() = _videoListLiveData

    private val _similarMoviesLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val similarMoviesLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _similarMoviesLiveData

    private val _isImageLoading: MutableLiveData<Boolean>
    val isImageLoading: LiveData<Boolean>
        get() = _isImageLoading

    private val _isVideoLoading: MutableLiveData<Boolean>
    val isVideoLoading: LiveData<Boolean>
        get() = _isVideoLoading

//    private val _isSimilarMovieLoading: MutableLiveData<Boolean>
//    val isSimilarMovieLoading: LiveData<Boolean>
//        get() = _isSimilarMovieLoading

    private val _imagesErrorState: MutableLiveData<Boolean>
    val imagesErrorState: LiveData<Boolean>
        get() = _imagesErrorState

    private val _videoErrorState: MutableLiveData<Boolean>
    val videoErrorState: LiveData<Boolean>
        get() = _videoErrorState

//    private val _SimilarErrorState: MutableLiveData<Boolean>
//    val SimilarErrorState: LiveData<Boolean>
//        get() = _SimilarErrorState

    private val _favoritedLiveData: MutableLiveData<Boolean>
    val favoritedLiveData: LiveData<Boolean>
        get() = _favoritedLiveData

    init {
        _imageListLiveData = MutableLiveData()
        _videoListLiveData = MutableLiveData()
        _similarMoviesLiveData = MutableLiveData()
        _favoritedLiveData = MutableLiveData()
        _detailLiveData = MutableLiveData()

        _videoErrorState = MutableLiveData()
        _imagesErrorState = MutableLiveData()

        _isVideoLoading = MutableLiveData()
        _isImageLoading = MutableLiveData()
    }

    fun SetFavoriteMovie(
        backdropPath: String?,
        id: Int,
        posterPath: String?,
        title: String,
        voteAverage: Double
    ) {
        val useCaseParams =
            FavoritedMovieDomainEntity(backdropPath, id, posterPath, title, voteAverage)

        val onErrorHandler: (Throwable) -> Unit = { throwable ->
            _ErrorToast.value = throwable.message
        }

        val onCompleteHandler: (Long) -> Unit = {
            _favoritedLiveData.value = true
        }

        val disposable =
            favorieMovie.execute(useCaseParams).subscribeBy(onErrorHandler, onCompleteHandler)

        addDisposable(disposable)
    }

    fun setUnFavoriteMovie(
        backdropPath: String?,
        id: Int,
        posterPath: String?,
        title: String,
        voteAverage: Double
    ) {
        val useCaseParams =
            FavoritedMovieDomainEntity(backdropPath, id, posterPath, title, voteAverage)

        val onErrorHandler: (Throwable) -> Unit = { throwable ->
            _ErrorToast.value = throwable.message
        }

        val onCompleteHandler: (Int) -> Unit = {
            _favoritedLiveData.value = false
        }

        val disposable =
            unfavoriteMovie.execute(useCaseParams).subscribeBy(onErrorHandler, onCompleteHandler)

        addDisposable(disposable)
    }

    fun getSimilarMovies(movieId: Int) {

        val disposable = getSimilarMovies.execute(movieId).cachedIn(viewModelScope).subscribeBy {

            _similarMoviesLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getMovieVideos(movieId: Int) {
        // _isLoading.value = true
        val disposable = getMovieVideosById.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _videoListLiveData.value =
                        movieVideoEntityUiDomainMapper.mapToUiEntity(response.data!!)
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

                            is UiComponentType.DIALOG -> {
                                _videoErrorState.value = true
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getMovieImages(movieId: Int) {
        // _isLoading.value = true
        val disposable = getMovieImagesById.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _imageListLiveData.value =
                        movieImageEntityUiDomainMapper.mapToUiEntity(response.data!!)
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

                            is UiComponentType.DIALOG -> {
                                _imagesErrorState.value = true
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getMovieDetail(movieId: Int) {
        // _isLoading.value = true
        val disposable = getDetailMovieUseCase.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    // _isLoading.value = false
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

                is MessageHolder.MESSAGE -> {
                    _ErrorSnackBar.value =
                        messageHolder.message
                }

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
