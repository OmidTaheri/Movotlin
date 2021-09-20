package ir.omidtaheri.daggercore.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.movotlin.di.modules.RemoteModule
import ir.omidtaheri.remote.datasource.MovieDetailRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.MovieRemoteDataSourceImp
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
interface RemoteDataSourceModule {

    @Singleton
    @Binds
    fun provideMovieDetailRemoteDataSource(
        datasource: MovieDetailRemoteDataSourceImp
    ): MovieDetailRemoteDataSourceInterface

    @Singleton
    @Binds
    fun provideMovieRemoteDataSource(
        datasource: MovieRemoteDataSourceImp
    ): MovieRemoteDataSourceInterface
}
