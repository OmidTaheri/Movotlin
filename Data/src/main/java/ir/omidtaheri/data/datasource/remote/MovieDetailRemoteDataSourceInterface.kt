package ir.omidtaheri.data.datasource.remote

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import kotlinx.coroutines.flow.Flow

interface MovieDetailRemoteDataSourceInterface {

    fun getMovieDetailById(movieId: Int): Flow<MovieDetailDataEntity>
    fun getMovieListByGenreId(params: Map<String, Int>): Flow<MultiMovieDataEntity>
    fun getMovieImagesById(movieId: Int): Flow<MovieImageDataEntity>
    fun getMovieVideosById(movieId: Int): Flow<MovieVideoDataEntity>
    fun getSimilarMovieById(movieId: Int, page: Int): Flow<MultiMovieDataEntity>
    fun searchMovieByQuery(query: String, page: Int): Flow<MultiMovieDataEntity>
}
