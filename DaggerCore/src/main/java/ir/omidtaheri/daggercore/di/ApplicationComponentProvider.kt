package ir.omidtaheri.daggercore.di

import ir.omidtaheri.movotlin.di.components.ApplicationComponent

interface ApplicationComponentProvider {

    fun provideApplicationComponent(): ApplicationComponent
}
