package ir.omidtaheri.mainpage.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModelFactory

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(viewmodel: MainViewModelFactory): ViewModelProvider.Factory {
        return viewmodel
    }
}
