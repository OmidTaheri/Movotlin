package ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetFavoriedMovieList
import ir.omidtaheri.favorite.mapper.FavoritedMovieEntityUiDomainMapper
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val getFavoriedMovieList: GetFavoriedMovieList,
    private val favoritedMovieEntityUiDomainMapper: FavoritedMovieEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<FavoriteViewModel> {

    override fun create(handle: SavedStateHandle): FavoriteViewModel {
        return FavoriteViewModel(
            getFavoriedMovieList,
            favoritedMovieEntityUiDomainMapper,
            handle,
            application
        )
    }

}
