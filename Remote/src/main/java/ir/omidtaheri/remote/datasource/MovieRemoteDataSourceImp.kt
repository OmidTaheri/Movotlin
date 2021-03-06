package ir.omidtaheri.remote.datasource

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.remote.mapper.GenreResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieApi
import javax.inject.Inject

class MovieRemoteDataSourceImp @Inject constructor(
    val movieApi: MovieApi,
    val movieResponseDtoMapper: MovieResponseToDataEntityMapper,
    val genreResponseDtoMapper: GenreResponseToDataEntityMapper
) :
    MovieRemoteDataSourceInterface {

    override fun getTopRatedMovies(page: Int): Single<MultiMovieDataEntity> {
        return movieApi.getTopRatedMovies(page).map {
            movieResponseDtoMapper.mapFromDTO(it)
        }
    }

    override fun getPopularMovies(page: Int): Single<MultiMovieDataEntity> {
        return movieApi.getPopularMovies(page).map {
            movieResponseDtoMapper.mapFromDTO(it)
        }
    }

    override fun getGenreList(): Single<List<GenreDataEntity>> {
        return movieApi.getGenreList().map {
            genreResponseDtoMapper.mapFromDTO(it)
        }
    }

    override fun getUpComingMovies(page: Int): Single<MultiMovieDataEntity> {
        return movieApi.getUpcomingMovies(page).map {
            movieResponseDtoMapper.mapFromDTO(it)
        }
    }
}
