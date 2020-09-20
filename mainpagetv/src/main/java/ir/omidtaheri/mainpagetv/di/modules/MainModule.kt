package ir.omidtaheri.mainpagetv.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel.MainViewModelFactory

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(viewmodel: MainViewModelFactory): ViewModelProvider.Factory {
        return viewmodel
    }
}
