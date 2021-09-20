package ir.omidtaheri.mainpagetv.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpagetv.ui.MainFragment.viewmodel.MainViewModelFactory

@Module
interface MainModule {

    @FragmentScope
    @Binds
    fun provideMainViewModel(viewModel: MainViewModelFactory): ViewModelProvider.Factory
}
