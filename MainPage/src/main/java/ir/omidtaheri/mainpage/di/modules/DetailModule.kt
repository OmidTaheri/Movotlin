package ir.omidtaheri.mainpage.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModelFactory

@Module
class DetailModule {

    @Provides
    fun provideDetailViewModel(viewmodel: DetailViewModelFactory): ViewModelProvider.Factory {
        return viewmodel
    }
}
