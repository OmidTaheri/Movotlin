package ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.detailpage.entity.FavoritedMovieUiEntity
import ir.omidtaheri.detailpage.entity.MovieDetailUiEntity
import ir.omidtaheri.detailpage.entity.MovieImageUiEntity
import ir.omidtaheri.detailpage.entity.MovieUiEntity
import ir.omidtaheri.detailpage.entity.MovieVideoUiEntity
import ir.omidtaheri.detailpage.mapper.FavoritedMovieEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.detailpage.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.FavorieMovie
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesSinglePage
import ir.omidtaheri.domain.interactor.UnfavoriteMovie
import ir.omidtaheri.domain.interactor.base.Schedulers
import java.util.concurrent.TimeUnit

class DetailViewModel(
    private val getDetailMovieUseCase: GetMovieDetail,
    private val getMovieImagesById: GetMovieImagesById,
    private val getMovieVideosById: GetMovieVideosById,
    private val getSimilarMovies: GetSimilarMoviesSinglePage,
    private val favorieMovie: FavorieMovie,
    private val unfavoriteMovie: UnfavoriteMovie,
    private val getFavoriedMovieList: GetFavoriedMovieList,
    private val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    private val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    private val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    private val schedulers: Schedulers,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    lateinit var recyclerViewsState: MutableList<LinearLayoutManager.SavedState?>

    val favoriteSubject: PublishSubject<FavoritedMovieUiEntity> = PublishSubject.create()
    lateinit var favoriteDisposable: Disposable

    private val _detailLiveData: MutableLiveData<MovieDetailUiEntity> = MutableLiveData()
    val detailLiveData: LiveData<MovieDetailUiEntity>
        get() = _detailLiveData

    private val _imageListLiveData: MutableLiveData<MovieImageUiEntity> = MutableLiveData()
    val imageListLiveData: LiveData<MovieImageUiEntity>
        get() = _imageListLiveData

    private val _videoListLiveData: MutableLiveData<MovieVideoUiEntity> = MutableLiveData()
    val videoListLiveData: LiveData<MovieVideoUiEntity>
        get() = _videoListLiveData

    private val _similarMoviesLiveData: MutableLiveData<PagingData<MovieUiEntity>> =
        MutableLiveData()
    val similarMoviesLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _similarMoviesLiveData

    private val _isImageLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isImageLoading: LiveData<Boolean>
        get() = _isImageLoading

    private val _isVideoLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isVideoLoading: LiveData<Boolean>
        get() = _isVideoLoading

    private val _imagesErrorState: MutableLiveData<Boolean> = MutableLiveData()
    val imagesErrorState: LiveData<Boolean>
        get() = _imagesErrorState

    private val _videoErrorState: MutableLiveData<Boolean> = MutableLiveData()
    val videoErrorState: LiveData<Boolean>
        get() = _videoErrorState

    private val _favoritedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val favoritedLiveData: LiveData<Boolean>
        get() = _favoritedLiveData

    private fun setFavoriteMovie(favoriteMovie: FavoritedMovieUiEntity): Single<Long> {
        val useCaseParams = favoritedMovieEntityUiDomainMapper.mapFromUiEntity(favoriteMovie)
        return favorieMovie.execute(useCaseParams)
    }

    private fun setUnFavoriteMovie(
        favoriteMovie: FavoritedMovieUiEntity
    ): Single<Int> {
        val useCaseParams = favoritedMovieEntityUiDomainMapper.mapFromUiEntity(favoriteMovie)
        return unfavoriteMovie.execute(useCaseParams)
    }

    fun setFavoriteSubjectObserver() {

        if (::favoriteDisposable.isInitialized) {
            deleteDisposable(favoriteDisposable)
        }

        val onErrorHandler: (Throwable) -> Unit = { throwable ->
            _errorToast.value = throwable.message
        }

        val favoriteonCompleteHandler: () -> Unit = {
            _favoritedLiveData.value = true
        }

        val unfavoriteonCompleteHandler: () -> Unit = {
            _favoritedLiveData.value = false
        }

        var isFavorite = false

        val observable = favoriteSubject.debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.subscribeOn)
            .switchMapSingle {
                isFavorite = it.isFavorite
                if (it.isFavorite) {
                    setFavoriteMovie(it)
                } else {
                    setUnFavoriteMovie(it)
                }
            }
            .observeOn(schedulers.observeOn)

        favoriteDisposable = if (isFavorite) {
            observable.subscribeBy(onErrorHandler, favoriteonCompleteHandler)
        } else {
            observable.subscribeBy(onErrorHandler, unfavoriteonCompleteHandler)
        }

        addDisposable(favoriteDisposable)
    }

    fun checkFavoriteStatus(movieId: Int) {

        val disposable = getFavoriedMovieList.execute(Unit).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {

                    _favoritedLiveData.value = false
                    response.data?.forEach {
                        if (it.id == movieId) {
                            _favoritedLiveData.value = true
                            return@forEach
                        }
                    }
                }

                is DataState.ERROR -> {
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                handleSnackBarError(errorDataState as DataState.ERROR<Any>)
                                _favoritedLiveData.value = false
                            }

                            is UiComponentType.TOAST -> {
                                handleToastError(errorDataState as DataState.ERROR<Any>)
                                _favoritedLiveData.value = false
                            }

                            is UiComponentType.DIALOG -> {
                                _favoritedLiveData.value = false
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }

    fun getSimilarMovies(movieId: Int) {

        val disposable = getSimilarMovies.execute(movieId).cachedIn(viewModelScope).subscribeBy {

            _similarMoviesLiveData.value = it.map { entity ->
                movieEntityUiDomainMapper.mapToUiEntity(entity)
            }
        }

        addDisposable(disposable)
    }

    fun getMovieVideos(movieId: Int) {
        val disposable = getMovieVideosById.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _videoListLiveData.value =
                        movieVideoEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
        val disposable = getMovieImagesById.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _imageListLiveData.value =
                        movieImageEntityUiDomainMapper.mapToUiEntity(response.data!!)
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
        val disposable = getDetailMovieUseCase.execute(movieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
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

        addDisposable(disposable)
    }

    private fun handleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {

                is MessageHolder.MESSAGE -> {
                    _errorSnackBar.value = "Error.Please Retry"
                }

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

    fun saveStateOfRecyclerViews(vararg layoutManagersState: LinearLayoutManager.SavedState?) {
        recyclerViewsState = mutableListOf()
        layoutManagersState.forEach {
            recyclerViewsState.add(it)
        }
    }

    fun restoreStateOfRecyclerViews(): MutableList<LinearLayoutManager.SavedState?> {
        return if (::recyclerViewsState.isInitialized)
            recyclerViewsState
        else mutableListOf()
    }

}
