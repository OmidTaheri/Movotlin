package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper

class MovieFullListViewModel(
    val GetPopularMoviesUseCase: GetPopularMovies,
    val GetTopRatedMoviesUseCase: GetTopRatedMovies,
    val GetUpcomingMoviesUseCase: GetUpcomingMovies,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _PoularLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val PoularLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _PoularLiveData

    private val _TopRateLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val TopRateLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _TopRateLiveData

    private val _UpComingLiveData: MutableLiveData<PagingData<MovieUiEntity>>
    val UpComingLiveData: LiveData<PagingData<MovieUiEntity>>
        get() = _UpComingLiveData

    init {
        _PoularLiveData = MutableLiveData()
        _TopRateLiveData = MutableLiveData()
        _UpComingLiveData = MutableLiveData()
    }

    fun getPopularMovieList() {

        val disposable = GetPopularMoviesUseCase.execute(Unit).subscribe {
            _PoularLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getTopRatedMovieList() {
        // _isTopRateLoading.value = true
        val disposable = GetTopRatedMoviesUseCase.execute(Unit).subscribeBy {
            _TopRateLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }

    fun getUpComingMovieList() {
        // _isUpComingLoading.value = true
        val disposable = GetUpcomingMoviesUseCase.execute(Unit).subscribeBy {
            _UpComingLiveData.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }
}
