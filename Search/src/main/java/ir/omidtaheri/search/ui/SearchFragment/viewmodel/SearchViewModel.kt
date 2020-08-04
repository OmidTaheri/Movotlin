package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams
import ir.omidtaheri.search.entity.MovieUiEntity
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper


class SearchViewModel(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) : BaseViewModel(application) {


    private val _DataLive: MutableLiveData<PagingData<MovieUiEntity>>
    val DataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _DataLive

//    private val _SearchErrorState: MutableLiveData<Boolean>
//    val SearchErrorState: LiveData<Boolean>
//        get() = _SearchErrorState


    init {
        _DataLive = MutableLiveData()
        //  _SearchErrorState = MutableLiveData()
    }


    fun SearchMovieByQuery(query: String, page: Int) {


        val disposable = searchMoviesByQuery.execute(query).subscribeBy {
            _DataLive.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }


}