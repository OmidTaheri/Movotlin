package ir.omidtaheri.movotlin.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.repository.DiscoverMovieRepository
import ir.omidtaheri.data.repository.MovieRepository
import ir.omidtaheri.data.repository.FavoriteMovieRepository
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay


@Module
class RepositoryModule {

    @Provides
    fun provideDiscoverMovieRepository(
        repository: DiscoverMovieRepository
    ): DiscoverMovieGateWay = repository


    @Provides
    fun provideMovieRepository(
        repository: MovieRepository
    ): MovieGateWay = repository

    @Provides
    fun provideFavoriteMovieRepository(
        repository: FavoriteMovieRepository
    ): FavoriteMovieGateWay = repository


}