package ir.omidtaheri.movotlin.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {


    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

}
