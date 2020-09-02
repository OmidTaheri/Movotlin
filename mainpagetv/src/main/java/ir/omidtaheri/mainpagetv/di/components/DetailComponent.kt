package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.DetailModule
import ir.omidtaheri.mainpagetv.ui.DetailFragment.DetailsFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent


@Component(dependencies = [ApplicationComponent::class], modules = [DetailModule::class])
interface DetailComponent {

    fun inject(fragment: DetailsFragment)
}
