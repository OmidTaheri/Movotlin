package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity

interface MovieDetailRemoteDataSourceInterface {

    fun GetMovieDetailById(MovieId: Int): Single<MovieDetailDataEntity>
    fun GetMovieListByGenreId(Params: Map<String, Any>): Single<MultiMovieDataEntity>
    fun GetMovieImagesById(MovieId: Int): Single<MovieImageDataEntity>
    fun GetMovieVideosById(MovieId: Int): Single<MovieVideoDataEntity>
    fun GetSimilarMovieById(MovieId: Int, page: Int): Single<MultiMovieDataEntity>
    fun SearchMovieByQuery(query: String, page: Int): Single<MultiMovieDataEntity>
}
