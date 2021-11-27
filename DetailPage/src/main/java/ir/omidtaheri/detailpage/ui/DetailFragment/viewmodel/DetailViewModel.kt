package ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.detailpage.entity.*
import ir.omidtaheri.detailpage.mapper.*
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    lateinit var recyclerViewsState: MutableList<LinearLayoutManager.SavedState?>


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

    fun setFavoriteMovie(favoriteMovie: FavoritedMovieUiEntity) {
        val useCaseParams = favoritedMovieEntityUiDomainMapper.mapFromUiEntity(favoriteMovie)
        viewModelScope.launch {
            try {
                favorieMovie.execute(useCaseParams)
                _favoritedLiveData.value = true
            } catch (e: Throwable) {
                _errorSnackBar.value = "Favorite Error.Please Retry"
            }

        }
    }

    fun setUnFavoriteMovie(
        favoriteMovie: FavoritedMovieUiEntity
    ) {
        val useCaseParams = favoritedMovieEntityUiDomainMapper.mapFromUiEntity(favoriteMovie)
        viewModelScope.launch {
            try {
                unfavoriteMovie.execute(useCaseParams)
                _favoritedLiveData.value = false
            } catch (e: Throwable) {
                _errorSnackBar.value = "UnFavorite Error.Please Retry"
            }

        }
    }


    fun checkFavoriteStatus(movieId: Int) {

        viewModelScope.launch {
            getFavoriedMovieList.execute(Unit).collect { response ->
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


        }
    }

    fun getSimilarMovies(movieId: Int) {

        viewModelScope.launch {
            getSimilarMovies.execute(movieId).cachedIn(viewModelScope).collectLatest {

                _similarMoviesLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }


        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            getMovieVideosById.execute(movieId).collect { response ->
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
        }
    }

    fun getMovieImages(movieId: Int) {
        viewModelScope.launch {
            getMovieImagesById.execute(movieId).collect { response ->
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


        }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase.execute(movieId).collect { response ->
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


        }
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
