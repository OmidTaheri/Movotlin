package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.movotlin.di.modules.RemoteModule
import ir.omidtaheri.remote.datasource.MovieDetailRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.MovieRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.MovieReviewsRemoteDataSourceImp


@Module(includes = [RemoteModule::class ])
class RemoteDataSourceModule {

    @Provides
    fun provideMovieDetailRemoteDataSource(
        datasource: MovieDetailRemoteDataSourceImp
    ): MovieDetailRemoteDataSourceInterface = datasource


    @Provides
    fun provideMovieRemoteDataSource(
        datasource: MovieRemoteDataSourceImp
    ): MovieRemoteDataSourceInterface = datasource


    @Provides
    fun provideMovieReviewsRemoteDataSource(
        datasource: MovieReviewsRemoteDataSourceImp
    ): MovieReviewsRemoteDataSourceInterface = datasource
}