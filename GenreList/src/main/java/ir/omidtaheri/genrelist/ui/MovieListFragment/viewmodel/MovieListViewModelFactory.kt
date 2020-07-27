package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.mapper.MultiMovieEntityUiDomainMapper

import javax.inject.Inject

class MovieListViewModelFactory @Inject constructor(
    val getMovieListByGenreId: GetMovieListByGenreId,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(
            getMovieListByGenreId,
            multiMovieEntityUiDomainMapper,
            application
        ) as T
    }
}