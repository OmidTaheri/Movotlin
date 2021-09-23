package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper
import java.util.concurrent.TimeUnit

class SearchViewModel(
    private val searchMoviesByQuery: SearchMoviesByQuery,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val schedulers: Schedulers,
    private val state: SavedStateHandle,
    private val mApplication: Application
) : BaseAndroidViewModel(mApplication, state) {

    private var recyclerViewState: LinearLayoutManager.SavedState? = null
    private var searchQueryState: String? = null

    val searchSubject: PublishSubject<String> = PublishSubject.create()
    private lateinit var currentDisposable: Disposable


    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive


    fun setSearchSubjectObserver() {
        if (::currentDisposable.isInitialized) {
            deleteDisposable(currentDisposable)
        }

        currentDisposable = searchSubject.debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.subscribeOn)
            .filter {
                it.isNotEmpty()
            }
            .distinctUntilChanged()
            .switchMap {
                _isLoading.postValue(true)
                searchMoviesByQuery.execute(it).cachedIn(viewModelScope)
            }
            .observeOn(schedulers.observeOn)
            .subscribeBy {
                _dataLive.value = it.map {
                    movieEntityUiDomainMapper.mapToUiEntity(it)
                }
            }

        addDisposable(currentDisposable)

    }


    fun initSearch(query: String) {

        val disposable = searchMoviesByQuery.execute(query).cachedIn(viewModelScope)
            .subscribeBy {
                _dataLive.value = it.map { entity ->
                    movieEntityUiDomainMapper.mapToUiEntity(entity)
                }
            }

        addDisposable(disposable)

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
