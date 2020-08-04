package ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetPopularMovies
import ir.omidtaheri.domain.interactor.GetTopRatedMovies
import ir.omidtaheri.domain.interactor.GetUpcomingMovies
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper

import javax.inject.Inject

class MovieFullListViewModelFactory @Inject constructor(
    val GetPopularMoviesUseCase: GetPopularMovies,
    val GetTopRatedMoviesUseCase: GetTopRatedMovies,
    val GetUpcomingMoviesUseCase: GetUpcomingMovies,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieFullListViewModel(
            GetPopularMoviesUseCase,
            GetTopRatedMoviesUseCase,
            GetUpcomingMoviesUseCase,
            movieEntityUiDomainMapper,
            application
        ) as T
    }
}