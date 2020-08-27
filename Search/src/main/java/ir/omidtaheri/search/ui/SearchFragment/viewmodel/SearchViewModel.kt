package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper

class SearchViewModel(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) : BaseViewModel(application) {

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

    fun searchMovieByQuery(query: String, page: Int) {

        val disposable = searchMoviesByQuery.execute(query).subscribeBy {
            _dataLive.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }
}
