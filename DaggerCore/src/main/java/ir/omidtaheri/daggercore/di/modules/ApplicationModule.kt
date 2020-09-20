package ir.omidtaheri.movotlin.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ir.omidtaheri.daggercore.di.schedulers.AppScheduler

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideSchedulars(schedulers: AppScheduler): ir.omidtaheri.domain.interactor.base.Schedulers =
        schedulers
}
