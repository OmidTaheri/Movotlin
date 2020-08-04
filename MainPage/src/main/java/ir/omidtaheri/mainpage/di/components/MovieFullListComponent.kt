package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.MainModule
import ir.omidtaheri.mainpage.di.modules.MovieFullListModule
import ir.omidtaheri.mainpage.ui.MainFragment.MainFragment
import ir.omidtaheri.mainpage.ui.MovieFullList.MovieFullListFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [MovieFullListModule::class])
interface MovieFullListComponent {

    fun inject(fragment: MovieFullListFragment)
}