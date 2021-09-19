package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class MovieFullListViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val getTopRatedMoviesUseCase: GetTopRatedMovies,
    private val getUpcomingMoviesUseCase: GetUpcomingMovies,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<MovieFullListViewModel> {
    override fun create(handle: SavedStateHandle): MovieFullListViewModel {
        return MovieFullListViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            movieEntityUiDomainMapper,
            handle,
            application
        )
    }

}
