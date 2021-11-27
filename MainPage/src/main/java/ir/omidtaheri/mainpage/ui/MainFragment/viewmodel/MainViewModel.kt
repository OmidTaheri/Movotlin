package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

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
import ir.omidtaheri.domain.interactor.GetPopularMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesSinglePage
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesSinglePage,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesSinglePage,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesSinglePage,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    lateinit var recyclerViewsState: MutableList<LinearLayoutManager.SavedState?>

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
        viewModelScope.launch {
            getPopularMoviesUseCase.execute(Unit).cachedIn(viewModelScope).collectLatest {
                _poularLiveData.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }
        }
    }

    fun getTopRatedMovieList() {

        viewModelScope.launch {
            getTopRatedMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
                .collectLatest {
                    _topRateLiveData.value = it.map { entity ->
                        movieEntityUiDomainMapper.mapToUiEntity(entity)
                    }
                }
        }
    }

    fun getUpComingMovieList() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
                .collectLatest {
                    _upComingLiveData.value = it.map { entity ->
                        movieEntityUiDomainMapper.mapToUiEntity(entity)
                    }
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
