package ir.omidtaheri.remote.service

import ir.omidtaheri.remote.entity.response.GenreResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResponseRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    /**
     * Get the list of the top rated movies
     */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MultiMovieResponseRemoteEntity

    /**
     * Get the list of the popular movies
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MultiMovieResponseRemoteEntity

    /**
     * Get the list of the upcoming movies
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): MultiMovieResponseRemoteEntity

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreResponseRemoteEntity
}
