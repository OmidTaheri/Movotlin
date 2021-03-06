package ir.omidtaheri.remote.service

import io.reactivex.Single
import ir.omidtaheri.remote.entity.response.MovieDetailResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MovieImagesResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MovieVideosResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResponseRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieDetailApi {

    @GET("movie/{movie_id}")
    fun getMovieDetailById(@Path("movie_id") movieId: Int): Single<MovieDetailResponseRemoteEntity>

    @GET("movie/{movie_id}/images")
    fun getMovieImagesById(@Path("movie_id") movieId: Int): Single<MovieImagesResponseRemoteEntity>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideosById(@Path("movie_id") movieId: Int): Single<MovieVideosResponseRemoteEntity>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMoviesById(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Single<MultiMovieResponseRemoteEntity>

    @GET("search/movie")
    fun searchMovieByQuery(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<MultiMovieResponseRemoteEntity>

    @GET("discover/movie")
    fun getMovieListByGenreId(@QueryMap params: Map<String, Int>): Single<MultiMovieResponseRemoteEntity>
}
