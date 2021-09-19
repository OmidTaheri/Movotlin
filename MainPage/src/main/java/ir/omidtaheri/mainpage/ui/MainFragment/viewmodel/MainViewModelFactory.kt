package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetPopularMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesSinglePage
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesSinglePage
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesSinglePage,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesSinglePage,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesSinglePage,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<MainViewModel> {

    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            movieEntityUiDomainMapper,
            handle,
            application
        )
    }
}
