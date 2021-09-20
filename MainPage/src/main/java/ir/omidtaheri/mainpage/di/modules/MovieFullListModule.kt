package ir.omidtaheri.mainpage.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModel
import ir.omidtaheri.mainpage.ui.MovieFullList.viewmodel.MovieFullListViewModelFactory

@Module
interface MovieFullListModule {

    @FragmentScope
    @Binds
    fun provideMovieFullListViewModel(viewModel: MovieFullListViewModelFactory): ViewModelAssistedFactory<MovieFullListViewModel>
}
