package ir.omidtaheri.genrelist.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.genrelist.di.modules.MovieListModule
import ir.omidtaheri.genrelist.ui.MovieListFragment.MovieListFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [MovieListModule::class])
interface MovieListComponent {

    fun inject(fragment: MovieListFragment)
}
