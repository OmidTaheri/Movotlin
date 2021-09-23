package ir.omidtaheri.detailpage.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel.DetailViewModel
import ir.omidtaheri.detailpage.ui.DetailFragment.viewmodel.DetailViewModelFactory


@Module
interface DetailModule {

    @FragmentScope
    @Binds
    fun provideDetailViewModel(viewModel: DetailViewModelFactory): ViewModelAssistedFactory<DetailViewModel>
}
