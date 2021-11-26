package ir.omidtaheri.remote.datasource

import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.remote.mapper.GenreResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDataSourceImp @Inject constructor(
    private val movieApi: MovieApi,
    private val movieResponseDtoMapper: MovieResponseToDataEntityMapper,
    private val genreResponseDtoMapper: GenreResponseToDataEntityMapper
) :
    MovieRemoteDataSourceInterface {

    override fun getTopRatedMovies(page: Int): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseDtoMapper.mapFromDTO(
                    movieApi.getTopRatedMovies(page)
                )
            )
        }
    }

    override fun getPopularMovies(page: Int): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseDtoMapper.mapFromDTO(
                    movieApi.getPopularMovies(page)
                )
            )
        }
    }

    override fun getGenreList(): Flow<List<GenreDataEntity>> {
        return flow {
            emit(
                genreResponseDtoMapper.mapFromDTO(
                    movieApi.getGenreList()
                )
            )
        }
    }

    override fun getUpComingMovies(page: Int): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseDtoMapper.mapFromDTO(
                    movieApi.getUpcomingMovies(page)
                )
            )
        }
    }
}
