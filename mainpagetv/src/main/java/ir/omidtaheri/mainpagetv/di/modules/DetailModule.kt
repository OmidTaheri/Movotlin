package ir.omidtaheri.mainpagetv.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.mainpagetv.ui.DetailFragment.viewmodel.DetailViewModelFactory

@Module
interface DetailModule {

    @FragmentScope
    @Binds
    fun provideDetailViewModel(viewModel: DetailViewModelFactory): ViewModelAssistedFactory<DetailViewModel>
}
