package ir.omidtaheri.remote.datasource

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.remote.mapper.MovieResponseDtoMapper
import ir.omidtaheri.remote.service.MovieApi

class MovieRemoteDataSourceImp(
    val movieApi: MovieApi,
    val movieResponseDtoMapper: MovieResponseDtoMapper
) :
    MovieRemoteDataSourceInterface {
    override fun GetMovies(): Single<List<MovieDataEntity>> {
        return movieApi.getPopularMovies().map {
            movieResponseDtoMapper.mapFromDTO(it)
        }
    }
}