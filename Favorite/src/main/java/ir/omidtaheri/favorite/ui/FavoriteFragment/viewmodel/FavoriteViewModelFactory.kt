package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.domain.interactor.GetFavoriedMovieListByFlowable
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    val getFavoriedMovieListByFlowable: GetFavoriedMovieListByFlowable,
    val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            getFavoriedMovieListByFlowable,
            favoritedMovieEntityUiDomainMapper,
            application
        ) as T
    }
}
