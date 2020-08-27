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

    override fun getMovieDetailById(MovieId: Int): Single<MovieDetailDataEntity> {
        return movieDetailApi.getMovieDetailById(MovieId).map {
            movieDetailResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieListByGenreId(params: Map<String, Any>): Single<MultiMovieDataEntity> {
        return movieDetailApi.getMovieListByGenreId(params).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieImagesById(MovieId: Int): Single<MovieImageDataEntity> {
        return movieDetailApi.getMovieImagesById(MovieId).map {
            movieImagesResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getMovieVideosById(MovieId: Int): Single<MovieVideoDataEntity> {
        return movieDetailApi.getMovieVideosById(MovieId).map {
            movieVideosResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun getSimilarMovieById(MovieId: Int, page: Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.getSimilarMoviesById(MovieId, page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun searchMovieByQuery(query: String, page: Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.searchMovieByQuery(query, page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }
}
