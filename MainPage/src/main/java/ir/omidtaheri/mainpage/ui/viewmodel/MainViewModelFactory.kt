package ir.omidtaheri.mainpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    val GetMoviesUseCase: GetMovies,
    val UiDomainMapper: MovieEntityUiDomainMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(GetMoviesUseCase, UiDomainMapper) as T
    }
}