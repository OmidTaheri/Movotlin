package ir.omidtaheri.movotlin.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

import ir.omidtaheri.daggercore.di.schedulers.AppScheduler

@Module
class ApplicationModule(val appcontext: Context) {


    @Provides
    fun provideAppContext() = appcontext


    @Provides
    fun provideSchedulars(schedulers: AppScheduler): ir.omidtaheri.domain.interactor.base.Schedulers =
        schedulers


}