package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.entity.response.GenreResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResponseRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {


    /**
     * Get the list of the top rated movies
     */
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Single<MultiMovieResponseRemoteEntity>


    /**
     * Get the list of the popular movies
     */
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MultiMovieResponseRemoteEntity>


    /**
     * Get the list of the upcoming movies
     */
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int): Single<MultiMovieResponseRemoteEntity>


    @GET("genre/movie/list")
    fun getGenreList(): Single<GenreResponseRemoteEntity>

}