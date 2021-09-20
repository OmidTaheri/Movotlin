package ir.omidtaheri.search.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModel
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModelFactory

@Module
interface SearchModule {

    @FragmentScope
    @Binds
    fun provideSearchViewModelFactory(factory: SearchViewModelFactory): ViewModelAssistedFactory<SearchViewModel>
}
