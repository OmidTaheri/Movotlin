package ir.omidtaheri.movotlin.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ir.omidtaheri.daggercore.di.modules.LocalDataSourceModule
import ir.omidtaheri.daggercore.di.modules.RemoteDataSourceModule
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, LocalDataSourceModule::class, RemoteDataSourceModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(applicationClass: Application)
    fun movieGateWayRepo(): MovieGateWay
    fun movieDetailGateWayRepo(): DiscoverMovieGateWay
    fun favoriteMovieGateWayRepo(): FavoriteMovieGateWay
    fun application(): Application

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance @Named("dbName") dbName: String,
            @BindsInstance @Named("url") baseUrl: String,
            @BindsInstance @Named("apiKey") apiKey: String
        ): ApplicationComponent
    }
}
