package ir.omidtaheri.genrelist.di.components

import dagger.Component
import ir.omidtaheri.genrelist.di.modules.GenreModule
import ir.omidtaheri.genrelist.ui.GenreFragment.GenreFragment

import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [GenreModule::class])
interface GenreComponent {

    fun inject(fragment: GenreFragment)
}