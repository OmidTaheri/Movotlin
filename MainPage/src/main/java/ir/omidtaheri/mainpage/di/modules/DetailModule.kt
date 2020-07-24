package ir.omidtaheri.mainpage.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModelFactory
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModelFactory


@Module
class DetailModule {

    @Provides
    fun provideDetailViewModel(viewmodel: DetailViewModelFactory): ViewModelProvider.Factory {
        return viewmodel

    }
}