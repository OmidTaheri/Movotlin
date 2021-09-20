package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.di.modules.DetailModule
import ir.omidtaheri.mainpage.ui.DetailFragment.DetailFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [DetailModule::class])
interface DetailComponent {

    fun inject(fragment: DetailFragment)
}
