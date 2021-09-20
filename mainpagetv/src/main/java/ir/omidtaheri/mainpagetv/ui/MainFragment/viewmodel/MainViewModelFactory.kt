package ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetPopularMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetTopRatedMoviesWithoutPaging
import ir.omidtaheri.domain.interactor.GetUpcomingMoviesWithoutPaging
import ir.omidtaheri.mainpagetv.mapper.MultiMovieEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesWithoutPaging,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesWithoutPaging,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesWithoutPaging,
    private val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            multiMovieEntityUiDomainMapper,
            handle,
            application
        )
    }

}
