package ir.omidtaheri.search.ui.SearchFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.SearchMoviesByQuery
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.search.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val searchMoviesByQuery: SearchMoviesByQuery,
    private val movieEntityUiDomainMapper: MovieEntityUiDomainMapper,
    private val schedulers: Schedulers,
    private val application: Application
) : ViewModelAssistedFactory<SearchViewModel> {
    override fun create(handle: SavedStateHandle): SearchViewModel {
        return SearchViewModel(
            searchMoviesByQuery,
            movieEntityUiDomainMapper,
            schedulers,
            handle,
            application
        )
    }
}
