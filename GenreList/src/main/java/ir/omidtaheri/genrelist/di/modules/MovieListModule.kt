package ir.omidtaheri.genrelist.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModel
import ir.omidtaheri.genrelist.ui.MovieListFragment.viewmodel.MovieListViewModelFactory

@Module
interface MovieListModule {

    @FragmentScope
    @Binds
    fun provideMovieListViewModel(viewModel: MovieListViewModelFactory): ViewModelAssistedFactory<MovieListViewModel>
}
