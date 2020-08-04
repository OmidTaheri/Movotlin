package ir.omidtaheri.mainpage.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModelFactory
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModelFactory


@Module
class MovieFullListModule {

    @Provides
    fun provideMovieFullListViewModel(viewmodel: MovieFullListViewModelFactory): ViewModelProvider.Factory {
        return viewmodel

    }
}