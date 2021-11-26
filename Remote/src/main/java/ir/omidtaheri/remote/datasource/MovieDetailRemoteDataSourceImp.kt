package ir.omidtaheri.remote.datasource

import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.remote.mapper.MovieDetailResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieImagesResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieVideosResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieDetailApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRemoteDataSourceImp @Inject constructor(
    private val movieDetailApi: MovieDetailApi,
    private val movieDetailResponseToDataEntityMapper: MovieDetailResponseToDataEntityMapper,
    private val movieImagesResponseToDataEntityMapper: MovieImagesResponseToDataEntityMapper,
    private val movieVideosResponseToDataEntityMapper: MovieVideosResponseToDataEntityMapper,
    private val movieResponseToDataEntityMapper: MovieResponseToDataEntityMapper
) :
    MovieDetailRemoteDataSourceInterface {

    override fun getMovieDetailById(movieId: Int): Flow<MovieDetailDataEntity> {
        return flow {
            emit(
                movieDetailResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.getMovieDetailById(
                        movieId
                    )
                )
            )
        }
    }

    override fun getMovieListByGenreId(params: Map<String, Int>): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.getMovieListByGenreId(
                        params
                    )
                )
            )
        }
    }

    override fun getMovieImagesById(movieId: Int): Flow<MovieImageDataEntity> {
        return flow {
            emit(
                movieImagesResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.getMovieImagesById(
                        movieId
                    )
                )
            )
        }
    }

    override fun getMovieVideosById(movieId: Int): Flow<MovieVideoDataEntity> {
        return flow {
            emit(
                movieVideosResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.getMovieVideosById(
                        movieId
                    )
                )
            )
        }
    }

    override fun getSimilarMovieById(movieId: Int, page: Int): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.getSimilarMoviesById(
                        movieId,
                        page
                    )
                )
            )
        }
    }

    override fun searchMovieByQuery(query: String, page: Int): Flow<MultiMovieDataEntity> {
        return flow {
            emit(
                movieResponseToDataEntityMapper.mapFromDTO(
                    movieDetailApi.searchMovieByQuery(
                        query,
                        page
                    )
                )
            )
        }
    }
}
