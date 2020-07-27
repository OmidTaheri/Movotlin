package ir.omidtaheri.remote.datasource

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.entity.*
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


    override fun GetMovieDetailById(MovieId: Int): Single<MovieDetailDataEntity> {
        return movieDetailApi.getMovieDetailById(MovieId).map {
            movieDetailResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun GetMovieListByGenreId(params: Map<String, Any>): Single<MultiMovieDataEntity> {
        return movieDetailApi.getMovieListByGenreId(params).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun GetMovieImagesById(MovieId: Int): Single<MovieImageDataEntity> {
        return movieDetailApi.getMovieImagesById(MovieId).map {
            movieImagesResponseToDataEntityMapper.mapFromDTO(it)
        }
    }


    override fun GetMovieVideosById(MovieId: Int): Single<MovieVideoDataEntity> {
        return movieDetailApi.getMovieVideosById(MovieId).map {
            movieVideosResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun GetSimilarMovieById(MovieId: Int,page:Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.getSimilarMoviesById(MovieId,page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }

    override fun SearchMovieByQuery(query: String,page:Int): Single<MultiMovieDataEntity> {
        return movieDetailApi.searchMovieByQuery(query,page).map {
            movieResponseToDataEntityMapper.mapFromDTO(it)
        }
    }
}