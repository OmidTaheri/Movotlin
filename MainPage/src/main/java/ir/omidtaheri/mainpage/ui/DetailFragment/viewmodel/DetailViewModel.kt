package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
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

    private val _DetailLiveData: MutableLiveData<MovieDetailUiEntity>
    val DetailLiveData: LiveData<MovieDetailUiEntity>
        get() = _DetailLiveData

    private val _ImageListLiveData: MutableLiveData<MovieImageUiEntity>
    val ImageListLiveData: LiveData<MovieImageUiEntity>
        get() = _ImageListLiveData

    private val _VideoListLiveData: MutableLiveData<MovieVideoUiEntity>
    val VideoListLiveData: LiveData<MovieVideoUiEntity>
        get() = _VideoListLiveData

    private val _SimilarMoviesLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val SimilarMoviesLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _SimilarMoviesLiveData

    private val _isImageLoading: MutableLiveData<Boolean>
    val isImageLoading: LiveData<Boolean>
        get() = _isImageLoading

    private val _isVideoLoading: MutableLiveData<Boolean>
    val isVideoLoading: LiveData<Boolean>
        get() = _isVideoLoading

//    private val _isSimilarMovieLoading: MutableLiveData<Boolean>
//    val isSimilarMovieLoading: LiveData<Boolean>
//        get() = _isSimilarMovieLoading

    private val _ImagesErrorState: MutableLiveData<Boolean>
    val ImagesErrorState: LiveData<Boolean>
        get() = _ImagesErrorState

    private val _VideoErrorState: MutableLiveData<Boolean>
    val VideoErrorState: LiveData<Boolean>
        get() = _ImagesErrorState

//    private val _SimilarErrorState: MutableLiveData<Boolean>
//    val SimilarErrorState: LiveData<Boolean>
//        get() = _SimilarErrorState

    private val _FavoritedLiveData: MutableLiveData<Boolean>
    val FavoritedLiveData: LiveData<Boolean>
        get() = _FavoritedLiveData

    init {
        _ImageListLiveData = MutableLiveData()
        _VideoListLiveData = MutableLiveData()
        _SimilarMoviesLiveData = MutableLiveData()
        _FavoritedLiveData = MutableLiveData()
        _DetailLiveData = MutableLiveData()

        _VideoErrorState = MutableLiveData()
        _ImagesErrorState = MutableLiveData()

        _isVideoLoading = MutableLiveData()
        _isImageLoading = MutableLiveData()
    }

    fun SetFavoriteMovie(
        backdrop_path: String?,
        id: Int,
        poster_path: String?,
        title: String,
        vote_average: Double
    ) {
        val UseCaseParams =
            FavoritedMovieDomainEntity(backdrop_path, id, poster_path, title, vote_average)

        val OnErrorHandler: (Throwable) -> Unit = { throwable ->
            _ErrorToast.value = throwable.message
        }

        val OnCompleteHandler: (Long) -> Unit = {
            _FavoritedLiveData.value = true
        }

        val disposable =
            favorieMovie.execute(UseCaseParams).subscribeBy(OnErrorHandler, OnCompleteHandler)

        addDisposable(disposable)
    }

    fun SetUnFavoriteMovie(
        backdrop_path: String?,
        id: Int,
        poster_path: String?,
        title: String,
        vote_average: Double
    ) {
        val UseCaseParams =
            FavoritedMovieDomainEntity(backdrop_path, id, poster_path, title, vote_average)

        val OnErrorHandler: (Throwable) -> Unit = { throwable ->
            _ErrorToast.value = throwable.message
        }

        val OnCompleteHandler: (Int) -> Unit = {
            _FavoritedLiveData.value = false
        }

        val disposable =
            unfavoriteMovie.execute(UseCaseParams).subscribeBy(OnErrorHandler, OnCompleteHandler)

        addDisposable(disposable)
    }

    fun getSimilarMovies(MovieId: Int) {

        val disposable = getSimilarMovies.execute(MovieId).subscribeBy {

            _SimilarMoviesLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getMovieVideos(MovieId: Int) {
        // _isLoading.value = true
        val disposable = getMovieVideosById.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _VideoListLiveData.value =
                        movieVideoEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }

                is DataState.ERROR -> {
                    // _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _VideoErrorState.value = true
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getMovieImages(MovieId: Int) {
        // _isLoading.value = true
        val disposable = getMovieImagesById.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    // _isLoading.value = false
                    _ImageListLiveData.value =
                        movieImageEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }

                is DataState.ERROR -> {
                    // _isLoading.value = false
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                HandleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                HandleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                                _ImagesErrorState.value = true
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getMovieDetail(MovieId: Int) {
        // _isLoading.value = true
        val disposable = getDetailMovieUseCase.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DetailLiveData.value =
                        movieDetailEntityUiDomainMapper.mapToUiEntity(response.data!!)
                }

                is DataState.ERROR -> {
                    // _isLoading.value = false
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
