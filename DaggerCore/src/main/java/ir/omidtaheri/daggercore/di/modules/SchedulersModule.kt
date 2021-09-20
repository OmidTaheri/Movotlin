package ir.omidtaheri.daggercore.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.daggercore.di.schedulers.AppScheduler
import javax.inject.Singleton

@Module
interface SchedulersModule {

    @Singleton
    @Binds
    fun provideSchedulers(schedulers: AppScheduler): ir.omidtaheri.domain.interactor.base.Schedulers

}