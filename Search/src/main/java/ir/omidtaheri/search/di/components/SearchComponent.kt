package ir.omidtaheri.search.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.search.di.modules.SearchModule
import ir.omidtaheri.search.ui.SearchFragment.SearchFragment

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [SearchModule::class])
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}
