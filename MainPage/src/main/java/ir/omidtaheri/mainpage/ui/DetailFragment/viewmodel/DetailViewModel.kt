package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.*

import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams

import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpage.entity.MovieImageUiEntity
import ir.omidtaheri.mainpage.entity.MovieVideoUiEntity
import ir.omidtaheri.mainpage.entity.MultiMovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper


class DetailViewModel(
    val getDetailMovieUseCase: GetMovieDetail,
    val getMovieImagesById: GetMovieImagesById,
    val getMovieVideosById: GetMovieVideosById,
    val getSimilarMovies: GetSimilarMovies,
    val favorieMovie: FavorieMovie,
    val unfavoriteMovie: UnfavoriteMovie,
    val movieDetailEntityUiDomainMapper: MovieDetailEntityUiDomainMapper,
    val movieImageEntityUiDomainMapper: MovieImageEntityUiDomainMapper,
    val movieVideoEntityUiDomainMapper: MovieVideoEntityUiDomainMapper,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel<MovieDetailUiEntity>(application) {


    protected val _ImageListLiveData: MutableLiveData<MovieImageUiEntity>
    val ImageListLiveData: LiveData<MovieImageUiEntity>
        get() = _ImageListLiveData

    protected val _VideoListLiveData: MutableLiveData<MovieVideoUiEntity>
    val VideoListLiveData: LiveData<MovieVideoUiEntity>
        get() = _VideoListLiveData


    protected val _SimilarMoviesLiveData: MutableLiveData<MultiMovieUiEntity>
    val SimilarMoviesLiveData: LiveData<MultiMovieUiEntity>
        get() = _SimilarMoviesLiveData


    protected val _FavoritedLiveData: MutableLiveData<Boolean>
    val FavoritedLiveData: LiveData<Boolean>
        get() = _FavoritedLiveData


    init {
        _ImageListLiveData = MutableLiveData()
        _VideoListLiveData = MutableLiveData()
        _SimilarMoviesLiveData = MutableLiveData()
        _FavoritedLiveData = MutableLiveData()
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

        val OnCompleteHandler: () -> Unit = {
            _FavoritedLiveData.value = true
        }


        val disposable =
            favorieMovie.execute(UseCaseParams).subscribeBy(OnErrorHandler, OnCompleteHandler)

        addDisposable(disposable)
    }


    fun SetUnFavoriteMovie(MovieId: Int) {
        val OnErrorHandler: (Throwable) -> Unit = { throwable ->
            _ErrorToast.value = throwable.message
        }

        val OnCompleteHandler: () -> Unit = {
            _FavoritedLiveData.value = false
        }


        val disposable =
            unfavoriteMovie.execute(MovieId).subscribeBy(OnErrorHandler, OnCompleteHandler)

        addDisposable(disposable)
    }


    fun getSimilarMovies(MovieId: Int, page: Int) {
        _isLoading.value = true

        val UseCaseParams = GetSimilarMoviesParams(MovieId, page)
        val disposable = getSimilarMovies.execute(UseCaseParams).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _SimilarMoviesLiveData.value =
                        multiMovieEntityUiDomainMapper.mapToUiEntity(response.data!!)
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


    fun getMovieVideos(MovieId: Int) {
        _isLoading.value = true
        val disposable = getMovieVideosById.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _VideoListLiveData.value =
                        movieVideoEntityUiDomainMapper.mapToUiEntity(response.data!!)
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


    fun getMovieImages(MovieId: Int) {
        _isLoading.value = true
        val disposable = getMovieImagesById.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _ImageListLiveData.value =
                        movieImageEntityUiDomainMapper.mapToUiEntity(response.data!!)
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


    fun getMovieDetail(MovieId: Int) {
        _isLoading.value = true
        val disposable = getDetailMovieUseCase.execute(MovieId).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {
                    _isLoading.value = false
                    _DataLive.value = movieDetailEntityUiDomainMapper.mapToUiEntity(response.data!!)
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