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

    private val _DataLive: MutableLiveData<PagingData<MovieUiEntity>>
    val DataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _DataLive

    init {
        _DataLive = MutableLiveData()
    }

    fun getMovieListByGenre(GenreId: Int) {

        val disposable = getMovieListByGenreId.execute(GenreId).subscribeBy {

            _DataLive.value = it.map {
                movieEntityUiDomainMapper.mapToUiEntity(it)
            }
        }

        addDisposable(disposable)
    }
}
