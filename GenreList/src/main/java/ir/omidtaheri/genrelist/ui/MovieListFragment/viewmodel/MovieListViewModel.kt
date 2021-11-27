package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

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
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.entity.MovieUiEntity
import ir.omidtaheri.genrelist.mapper.MovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMovieListByGenreId: GetMovieListByGenreId,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private var recyclerViewState: LinearLayoutManager.SavedState? = null

    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive

    fun getMovieListByGenre(genreId: Int) {

        viewModelScope.launch {
            getMovieListByGenreId.execute(genreId).cachedIn(viewModelScope).collectLatest {

                _dataLive.value = it.map {
                    movieEntityUiDomainMapper.mapToUiEntity(it)
                }
            }

        }
    }

    fun saveFragmentState(
        layoutManagerState: LinearLayoutManager.SavedState?
    ) {
        recyclerViewState = layoutManagerState
    }

    fun restoreStateOfRecyclerView(): LinearLayoutManager.SavedState? {
        return recyclerViewState
    }
}
