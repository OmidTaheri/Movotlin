package ir.omidtaheri.remote.datasource

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieApi
import javax.inject.Inject

class MovieRemoteDataSourceImp  @Inject constructor(
    val movieApi: MovieApi,
    val movieResponseDtoMapper: MovieResponseToDataEntityMapper
) :
    MovieRemoteDataSourceInterface {
    override fun GetMovies(): Single<List<MovieDataEntity>> {
        return movieApi.getPopularMovies().map {
            movieResponseDtoMapper.mapFromDTO(it)
        }
    }
}