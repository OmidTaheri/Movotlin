package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.search.mapper.MultiMovieEntityUiDomainMapper

import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    val searchMoviesByQuery: SearchMoviesByQuery,
    val multiMovieEntityUiDomainMapper: MultiMovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(
            searchMoviesByQuery,
            multiMovieEntityUiDomainMapper,
            application
        ) as T
    }
}