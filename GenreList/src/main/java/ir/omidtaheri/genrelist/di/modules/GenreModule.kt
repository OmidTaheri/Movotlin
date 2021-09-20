package ir.omidtaheri.genrelist.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModel
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModelFactory

@Module
interface GenreModule {

    @FragmentScope
    @Binds
    fun provideGenreViewModel(viewModel: GenreViewModelFactory): ViewModelAssistedFactory<GenreViewModel>
}
