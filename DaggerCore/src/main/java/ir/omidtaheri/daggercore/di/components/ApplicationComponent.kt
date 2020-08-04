package ir.omidtaheri.movotlin.di.components

import android.app.Application
import dagger.Component
import ir.omidtaheri.daggercore.di.modules.LocalDataSourceModule
import ir.omidtaheri.daggercore.di.modules.RemoteDataSourceModule
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule

@Component(modules = [ApplicationModule::class, LocalDataSourceModule::class, RemoteDataSourceModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(applicationClass: Application)
    fun schedulers(): ir.omidtaheri.domain.interactor.base.Schedulers
    fun MovieGateWayRepo(): MovieGateWay
    fun MovieDetailGateWayRepo():DiscoverMovieGateWay
    fun FavoriteMovieGateWayRepo(): FavoriteMovieGateWay
    fun application():Application
}