package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.MainModule
import ir.omidtaheri.movotlin.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [MainModule::class])
interface MainComponent {
}