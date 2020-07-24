package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetMovies
import ir.omidtaheri.favorite.mapper.MovieEntityUiDomainMapper
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    val GetMoviesUseCase: GetMovies,
    val UiDomainMapper: MovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(GetMoviesUseCase, UiDomainMapper,application) as T
    }
}