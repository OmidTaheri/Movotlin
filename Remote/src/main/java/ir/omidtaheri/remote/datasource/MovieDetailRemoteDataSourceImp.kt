package ir.omidtaheri.remote.datasource

import io.reactivex.Single
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
import javax.inject.Inject

class MovieDetailRemoteDataSourceImp @Inject constructor(
    val movieDetailApi: MovieDetailApi,
    val movieDetailResponseToDataEntityMapper: MovieDetailResponseToDataEntityMapper,
    val movieImagesResponseToDataEntityMapper: MovieImagesResponseToDataEntityMapper,
    val movieVideosResponseToDataEntityMapper: MovieVideosResponseToDataEntityMapper,
    val movieResponseToDataEntityMapper: MovieResponseToDataEntityMapper
) :
    MovieDetailRemoteDataSourceInterface {

    override fun getMovieDetailById(movieId: Int): Single<MovieDetailDataEntity> {
        return movieDetailApi.getMovieDetailById(movieId).map {
            movieDetailResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieListByGenreId(params: Map<String, Any>): Single<MultiMovieDataEntity> {
        return movieDetailApi.getMovieListByGenreId(params).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieImagesById(movieId: Int): Single<MovieImageDataEntity> {
        return movieDetailApi.getMovieImagesById(movieId).map {
            movieImagesResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieVideosById(movieId: Int): Single<MovieVideoDataEntity> {
        return movieDetailApi.getMovieVideosById(movieId).map {
            movieVideosResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getSimilarMovieById(movieId: Int, page: Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.getSimilarMoviesById(movieId, page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun searchMovieByQuery(query: String, page: Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.searchMovieByQuery(query, page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }
}
