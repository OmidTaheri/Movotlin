package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.*
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MultiMovieEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    val GetPopularMoviesUseCase: GetPopularMoviesSinglePage,
    val GetTopRatedMoviesUseCase: GetTopRatedMoviesSinglePage,
    val GetUpcomingMoviesUseCase: GetUpcomingMoviesSinglePage,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            GetPopularMoviesUseCase,
            GetTopRatedMoviesUseCase,
            GetUpcomingMoviesUseCase,
            movieEntityUiDomainMapper,
            application
        ) as T
    }
}