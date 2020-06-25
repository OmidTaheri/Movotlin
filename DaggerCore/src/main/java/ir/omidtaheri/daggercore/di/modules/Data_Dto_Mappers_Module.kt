package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import ir.omidtaheri.remote.mapper.MovieDetailResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieReviewResponseToDataEntityMapper

@Module
class Data_Dto_Mappers_Module {

    @Provides
    fun provideMovieEntityDataLocalMapper(mapper: MovieEntityDataLocalMapper): MovieEntityDataLocalMapper {
        return mapper
    }

    @Provides
    fun provideMovieDetailResponseToDataEntityMapper(mapper: MovieDetailResponseToDataEntityMapper): MovieDetailResponseToDataEntityMapper =
        mapper

    @Provides
    fun provideMovieResponseToDataEntityMapper(mapper: MovieResponseToDataEntityMapper): MovieResponseToDataEntityMapper =
        mapper


    @Provides
    fun provideMovieReviewResponseToDataEntityMapper(mapper: MovieReviewResponseToDataEntityMapper): MovieReviewResponseToDataEntityMapper =
        mapper



}