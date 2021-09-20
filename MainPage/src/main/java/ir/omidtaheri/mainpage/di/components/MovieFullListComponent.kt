package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.di.modules.MovieFullListModule
import ir.omidtaheri.mainpage.ui.MovieFullList.MovieFullListFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [MovieFullListModule::class])
interface MovieFullListComponent {

    fun inject(fragment: MovieFullListFragment)
}
