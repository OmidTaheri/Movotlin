package ir.omidtaheri.mainpage.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModelFactory

@Module
interface DetailModule {

    @FragmentScope
    @Binds
    fun provideDetailViewModel(viewModel: DetailViewModelFactory): ViewModelAssistedFactory<DetailViewModel>
}
