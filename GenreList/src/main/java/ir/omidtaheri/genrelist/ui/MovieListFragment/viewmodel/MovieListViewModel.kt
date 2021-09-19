package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.entity.MovieUiEntity
import ir.omidtaheri.genrelist.mapper.MovieEntityUiDomainMapper

class MovieListViewModel(
    private val getMovieListByGenreId: GetMovieListByGenreId,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    private val _dataLive: MutableLiveData<PagingData<MovieUiEntity>> = MutableLiveData()
    val dataLive: LiveData<PagingData<MovieUiEntity>>
        get() = _dataLive

    fun getMovieListByGenre(genreId: Int) {

        val disposable =
            getMovieListByGenreId.execute(genreId).cachedIn(viewModelScope).subscribeBy {

                _dataLive.value = it.map {
                    movieEntityUiDomainMapper.mapToUiEntity(it)
                }
            }

        addDisposable(disposable)
    }
}
