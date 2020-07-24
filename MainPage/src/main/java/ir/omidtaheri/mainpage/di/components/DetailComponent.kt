package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.DetailModule
import ir.omidtaheri.mainpage.di.modules.MainModule
import ir.omidtaheri.mainpage.ui.DetailFragment.DetailFragment
import ir.omidtaheri.mainpage.ui.MainFragment.MainFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [DetailModule::class])
interface DetailComponent {

    fun inject(fragment: DetailFragment)
}