package ir.omidtaheri.favorite.di.components

import dagger.Component
import ir.omidtaheri.favorite.di.modules.FavoriteModule
import ir.omidtaheri.favorite.ui.FavoriteFragment.FavoriteFragment
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [FavoriteModule::class])
interface FavoriteComponent {
    fun inject(fragment: FavoriteFragment)
}
