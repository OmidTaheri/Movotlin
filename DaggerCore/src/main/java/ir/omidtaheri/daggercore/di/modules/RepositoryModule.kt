package ir.omidtaheri.movotlin.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.data.repository.DiscoverMovieRepository
import ir.omidtaheri.data.repository.FavoriteMovieRepository
import ir.omidtaheri.data.repository.MovieRepository
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideDiscoverMovieRepository(
        repository: DiscoverMovieRepository
    ): DiscoverMovieGateWay


    @Singleton
    @Binds
    fun provideMovieRepository(
        repository: MovieRepository
    ): MovieGateWay

    @Singleton
    @Binds
    fun provideFavoriteMovieRepository(
        repository: FavoriteMovieRepository
    ): FavoriteMovieGateWay
}
