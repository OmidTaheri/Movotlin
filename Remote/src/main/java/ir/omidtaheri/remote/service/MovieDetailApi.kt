package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.entity.response.*
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
        @Path("query") query: String,
        @Query("page") page: Int
    ): Single<MultiMovieResponseRemoteEntity>


    @GET("/discover/movie")
    fun getMovieListByGenreId(@QueryMap params: Map<String, Any>): Single<MultiMovieResponseRemoteEntity>

}