package ir.omidtaheri.remote.datasource

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.remote.mapper.MovieDetailResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieDetailApi

class MovieDetailRemoteDataSourceImp(
    val movieDetailApi: MovieDetailApi,
    val movieDetailResponseToDataEntityMapper: MovieDetailResponseToDataEntityMapper
) :
    MovieDetailRemoteDataSourceInterface {

    override fun GetMovieDetail(MovieId: Long): Single<MovieDetailDataEntity> {
        return movieDetailApi.getMovieDetails(MovieId).map {
            movieDetailResponseToDataEntityMapper.mapFromDTO(it)
        }
    }
}