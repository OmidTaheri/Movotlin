package ir.omidtaheri.remote.service

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
    suspend fun getMovieDetailById(@Path("movie_id") movieId: Int): MovieDetailResponseRemoteEntity

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImagesById(@Path("movie_id") movieId: Int): MovieImagesResponseRemoteEntity

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideosById(@Path("movie_id") movieId: Int): MovieVideosResponseRemoteEntity

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMoviesById(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): MultiMovieResponseRemoteEntity

    @GET("search/movie")
    suspend fun searchMovieByQuery(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MultiMovieResponseRemoteEntity

    @GET("discover/movie")
    suspend fun getMovieListByGenreId(@QueryMap params: Map<String, Int>): MultiMovieResponseRemoteEntity
}
