package ir.omidtaheri.search.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.search.ui.SearchFragment.viewmodel.SearchViewModelFactory

@Module
class SearchModule {

    @Provides
    fun provideSearchViewModelFactory(factory: SearchViewModelFactory): ViewModelProvider.Factory {
        return factory
    }
}
