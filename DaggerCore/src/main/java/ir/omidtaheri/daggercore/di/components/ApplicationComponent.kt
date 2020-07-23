package ir.omidtaheri.movotlin.di.components

import android.app.Application
import dagger.Component
import ir.omidtaheri.daggercore.di.modules.LocalDataSourceModule
import ir.omidtaheri.daggercore.di.modules.RemoteDataSourceModule
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule

@Component(modules = [ApplicationModule::class, LocalDataSourceModule::class, RemoteDataSourceModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(applicationClass: Application)

}