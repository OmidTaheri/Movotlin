package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

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

        val disposable = getPopularMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribe {
            _popularLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getTopRatedMovieList() {
        val disposable = getTopRatedMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribeBy {
            _topRateLiveData.value = it.map { entity ->
                movieEntityUiDomainMapper.mapToUiEntity(entity)
            }
        }

        addDisposable(disposable)
    }

    fun getUpComingMovieList() {
        val disposable = getUpcomingMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribeBy {
            _upComingLiveData.value = it.map { entity ->
                movieEntityUiDomainMapper.mapToUiEntity(entity)
            }
        }

        addDisposable(disposable)
    }
}
