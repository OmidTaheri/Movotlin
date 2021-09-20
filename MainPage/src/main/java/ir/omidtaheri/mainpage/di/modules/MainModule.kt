package ir.omidtaheri.mainpage.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModelFactory

@Module
interface MainModule {

    @FragmentScope
    @Binds
    fun provideMainViewModel(viewModel: MainViewModelFactory): ViewModelAssistedFactory<MainViewModel>
}
