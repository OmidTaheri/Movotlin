package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity

interface MovieDetailRemoteDataSourceInterface {

    fun getMovieDetailById(movieId: Int): Single<MovieDetailDataEntity>
    fun getMovieListByGenreId(params: Map<String, Int>): Single<MultiMovieDataEntity>
    fun getMovieImagesById(movieId: Int): Single<MovieImageDataEntity>
    fun getMovieVideosById(movieId: Int): Single<MovieVideoDataEntity>
    fun getSimilarMovieById(movieId: Int, page: Int): Single<MultiMovieDataEntity>
    fun searchMovieByQuery(query: String, page: Int): Single<MultiMovieDataEntity>
}
