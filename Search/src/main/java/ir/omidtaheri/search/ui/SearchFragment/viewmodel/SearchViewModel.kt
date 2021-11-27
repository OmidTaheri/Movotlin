package ir.omidtaheri.search.ui.SearchFragment.viewmodel

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
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesByQuery: SearchMoviesByQuery,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) : BaseAndroidViewModel(mApplication, state) {

    private var recyclerViewState: LinearLayoutManager.SavedState? = null
    private var searchQueryState: String? = null


    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive


    fun searchQuery(query: String) {
        viewModelScope.launch {
            searchMoviesByQuery.execute(query).cachedIn(viewModelScope)
                .collectLatest {
                    _dataLive.value = it.map { entity ->
                        movieEntityUiDomainMapper.mapToUiEntity(entity)
                    }
                }


        }
    }


    fun saveFragmentState(
        layoutManagerState: LinearLayoutManager.SavedState?,
        searchQuery: String?
    ) {
        recyclerViewState = layoutManagerState
        searchQueryState = searchQuery
    }

    fun restoreStateOfRecyclerView(): LinearLayoutManager.SavedState? {
        return recyclerViewState
    }

    fun restoreSearchQuery(): String? {
        return searchQueryState
    }

}
