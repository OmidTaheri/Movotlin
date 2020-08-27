package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(
            searchMoviesByQuery,
            movieEntityUiDomainMapper,
            application
        ) as T
    }
}
