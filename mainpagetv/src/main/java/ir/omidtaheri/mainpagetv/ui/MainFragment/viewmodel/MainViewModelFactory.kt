package ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.*
import ir.omidtaheri.mainpagetv.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    val getPopularMoviesUseCase: GetPopularMoviesWithoutPaging,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesWithoutPaging,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesWithoutPaging,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            multiMovieEntityUiDomainMapper,
            application
        ) as T
    }
}
