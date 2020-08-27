package ir.omidtaheri.genrelist.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModelFactory

@Module
class MovieListModule {

    @Provides
    fun provideMainViewModel(viewmodel: MovieListViewModelFactory): ViewModelProvider.Factory {
        return viewmodel
    }
}
