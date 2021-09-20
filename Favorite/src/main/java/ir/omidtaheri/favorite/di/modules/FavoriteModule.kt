package ir.omidtaheri.favorite.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModel
import ir.omidtaheri.favorite.ui.FavoriteFragment.viewmodel.FavoriteViewModelFactory

@Module
interface FavoriteModule {

    @FragmentScope
    @Binds
    fun provideFavoriteViewModel(viewModel: FavoriteViewModelFactory): ViewModelAssistedFactory<FavoriteViewModel>
}
