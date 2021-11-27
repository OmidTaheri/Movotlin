package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieFullListViewModel(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val getTopRatedMoviesUseCase: GetTopRatedMovies,
    private val getUpcomingMoviesUseCase: GetUpcomingMovies,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private val _popularLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val popularLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _popularLiveData

    private val _topRateLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val topRateLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _topRateLiveData

    private val _upComingLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val upComingLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _upComingLiveData

    fun getPopularMovieList() {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute(Unit).cachedIn(viewModelScope).collectLatest {
                _popularLiveData.value = it.map {
                    movieEntityUiDomainMapper.mapToUiEntity(it)
                }
            }
        }

    }

    fun getTopRatedMovieList() {
        viewModelScope.launch {
            getTopRatedMoviesUseCase.execute(Unit).cachedIn(viewModelScope).collectLatest {
                _topRateLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        }
    }

    fun getUpComingMovieList() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase.execute(Unit).cachedIn(viewModelScope).collectLatest {
                _upComingLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        }
    }
}
