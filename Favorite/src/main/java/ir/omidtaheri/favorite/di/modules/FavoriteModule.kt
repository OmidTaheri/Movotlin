package ir.omidtaheri.favorite.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModelFactory

@Module
class FavoriteModule {

    @Provides
    fun provideFavoriteViewModel(viewmodel: FavoriteViewModelFactory): ViewModelProvider.Factory {
        return viewmodel

    }
}