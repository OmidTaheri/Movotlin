package ir.omidtaheri.mainpagetv.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpagetv.di.modules.MainModule
import ir.omidtaheri.mainpagetv.ui.MainFragment.MainFragmentBrowse
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [MainModule::class])
interface MainComponent {

    fun inject(fragment: MainFragmentBrowse)
}
