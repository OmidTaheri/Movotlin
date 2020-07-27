package ir.omidtaheri.genrelist.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.genrelist.ui.GenreFragment.viewmodel.GenreViewModelFactory

@Module
class GenreModule {

    @Provides
    fun provideDetailViewModel(viewmodel: GenreViewModelFactory): ViewModelProvider.Factory {
        return viewmodel

    }
}