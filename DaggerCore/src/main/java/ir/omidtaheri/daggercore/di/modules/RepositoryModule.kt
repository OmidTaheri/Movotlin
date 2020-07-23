package ir.omidtaheri.movotlin.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.repository.MovieDetailRepository
import ir.omidtaheri.data.repository.MovieRepository
import ir.omidtaheri.data.repository.MovieReviewsRepository
import ir.omidtaheri.domain.gateway.MovieDetailGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay


@Module
class RepositoryModule {

    @Provides
    fun provideMovieDetailRepository(
        repository: MovieDetailRepository
    ): MovieDetailGateWay = repository


    @Provides
    fun provideMovieRepository(
        repository: MovieRepository
    ): MovieGateWay = repository

    @Provides
    fun provideMovieReviewsRepository(
        repository: MovieReviewsRepository
    ): MovieReviewsGateWay = repository


}