package ir.omidtaheri.search.di.components

import dagger.Component
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.search.di.modules.SearchModule

@Component(dependencies = [ApplicationComponent::class], modules = [SearchModule::class])
interface SearchComponent {
}