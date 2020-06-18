package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityMapper
import ir.omidtaheri.domain.entity.MovieDetailEntity

import ir.omidtaheri.domain.gateway.MovieDetailGateWay

class MovieDetailRepository(
    val movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface,
    val movieDetailEntityMapper: MovieDetailEntityMapper
) : MovieDetailGateWay {

    override fun GetMovieDetail(MovieId: Long): Single<MovieDetailEntity> {
        return movieDetailRemoteDataSource.GetMovieDetail(MovieId).map {
            movieDetailEntityMapper.mapFromDataEntity(it)
        }
    }
}