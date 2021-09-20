package ir.omidtaheri.movotlin.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.omidtaheri.daggercore.di.modules.SchedulersModule
import ir.omidtaheri.daggercore.di.schedulers.AppScheduler
import javax.inject.Singleton

@Module(includes = [SchedulersModule::class])
class ApplicationModule {


    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

}
