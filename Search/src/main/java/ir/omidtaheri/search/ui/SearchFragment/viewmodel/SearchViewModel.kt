package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper
import java.util.concurrent.TimeUnit

class SearchViewModel(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    val schedulers: Schedulers,
    application: Application
) : BaseViewModel(application) {

    val searchSubject: PublishSubject<String> = PublishSubject.create()
    lateinit var currentDisposable: Disposable


    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>>
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive

//    private val _SearchErrorState: MutableLiveData<Boolean>
//    val SearchErrorState: LiveData<Boolean>
//        get() = _SearchErrorState

    init {
        _dataLive = MutableLiveData()
        //  _SearchErrorState = MutableLiveData()
    }


    fun setSearchSubjectObserver() {
        if (::currentDisposable.isInitialized) {
            deleteDisposable(currentDisposable)
        }

        currentDisposable = searchSubject.debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.subscribeOn)
            .filter {
                !it.isEmpty()
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


}
