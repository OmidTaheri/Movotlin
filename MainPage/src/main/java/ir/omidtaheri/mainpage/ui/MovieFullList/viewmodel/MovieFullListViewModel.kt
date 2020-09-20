package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

class MovieFullListViewModel(
    val getPopularMoviesUseCase: GetPopularMovies,
    val getTopRatedMoviesUseCase: GetTopRatedMovies,
    val getUpcomingMoviesUseCase: GetUpcomingMovies,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _poularLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val poularLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _poularLiveData

    private val _topRateLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val topRateLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _topRateLiveData

    private val _upComingLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val upComingLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _upComingLiveData

    init {
        _poularLiveData = MutableLiveData()
        _topRateLiveData = MutableLiveData()
        _upComingLiveData = MutableLiveData()
    }

    fun getPopularMovieList() {

        val disposable = getPopularMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribe {
            _poularLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getTopRatedMovieList() {
        // _isTopRateLoading.value = true
        val disposable = getTopRatedMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribeBy {
            _topRateLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getUpComingMovieList() {
        // _isUpComingLoading.value = true
        val disposable = getUpcomingMoviesUseCase.execute(Unit).cachedIn(viewModelScope).subscribeBy {
            _upComingLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }
}
