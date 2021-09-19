package ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetMovieListByGenreId
import ir.omidtaheri.genrelist.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class MovieListViewModelFactory @Inject constructor(
    private val getMovieListByGenreId: GetMovieListByGenreId,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<MovieListViewModel> {

    override fun create(handle: SavedStateHandle): MovieListViewModel {
        return MovieListViewModel(
            getMovieListByGenreId,
            movieEntityUiDomainMapper,
            handle,
            application
        )
    }
}
