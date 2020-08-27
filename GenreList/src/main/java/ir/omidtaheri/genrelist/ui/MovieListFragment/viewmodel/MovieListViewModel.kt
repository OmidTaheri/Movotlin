package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.entity.MovieUiEntity
import ir.omidtaheri.genrelist.mapper.MovieEntityUiDomainMapper

class MovieListViewModel(
    val getMovieListByGenreId: GetMovieListByGenreId,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {

    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>>
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive

    init {
        _dataLive = MutableLiveData()
    }

    fun getMovieListByGenre(genreId: Int) {

        val disposable = getMovieListByGenreId.execute(genreId).subscribeBy {

            _dataLive.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }
}
