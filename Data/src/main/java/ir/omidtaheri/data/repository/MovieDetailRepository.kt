package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity

import ir.omidtaheri.domain.gateway.MovieDetailGateWay

class MovieDetailRepository(
    val movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface,
    val movieDetailEntityMapper: MovieDetailEntityDomainDataMapper
) : MovieDetailGateWay {

    override fun GetMovieDetail(MovieId: Long): Single<MovieDetailDomainEntity> {
        return movieDetailRemoteDataSource.GetMovieDetail(MovieId).map {
            movieDetailEntityMapper.mapFromDataEntity(it)
        }
    }
}