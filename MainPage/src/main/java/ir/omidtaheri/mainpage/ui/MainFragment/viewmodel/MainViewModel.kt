package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

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
import ir.omidtaheri.domain.interactor.GetPopularMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesSinglePage
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesSinglePage,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesSinglePage,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesSinglePage,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {


    private val _poularLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val poularLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _poularLiveData

    private val _topRateLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val topRateLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _topRateLiveData

    private val _upComingLiveData: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val upComingLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _upComingLiveData

    fun getPopularMovieList() {
        val disposable = getPopularMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
            .subscribe {
                _poularLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        addDisposable(disposable)
    }


    fun getTopRatedMovieList() {
        val disposable = getTopRatedMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
            .subscribeBy {
                _topRateLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        addDisposable(disposable)
    }

    fun getUpComingMovieList() {
        val disposable = getUpcomingMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
            .subscribeBy {
                _upComingLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        addDisposable(disposable)
    }
}
