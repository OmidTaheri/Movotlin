package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper

import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    val GetMoviesUseCase: GetMovies,
    val UiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(GetMoviesUseCase, UiDomainMapper,application) as T
    }
}