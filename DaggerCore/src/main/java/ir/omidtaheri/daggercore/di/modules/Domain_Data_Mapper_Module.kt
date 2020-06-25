package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieEntityDomainDataMapper
import ir.omidtaheri.data.mapper.ReviewEntityDomainDataMapper


@Module
class Domain_Data_Mapper_Module {


    @Provides
    fun provideMovieDetailEntityDomainDataMapper(mapper: MovieDetailEntityDomainDataMapper): MovieDetailEntityDomainDataMapper =
        mapper

    @Provides
    fun provideMovieEntityDomainDataMapper(mapper: MovieEntityDomainDataMapper): MovieEntityDomainDataMapper =
        mapper

    @Provides
    fun provideReviewEntityDomainDataMapper(mapper: ReviewEntityDomainDataMapper): ReviewEntityDomainDataMapper =
        mapper

}