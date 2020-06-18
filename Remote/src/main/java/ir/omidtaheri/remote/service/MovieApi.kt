package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.dto.response.GetMoviesResponse
import retrofit2.http.GET

interface MovieApi {


    /**
     * Get the list of the popular movies
     */
    @GET("movie/popular")
    fun getPopularMovies(): Single<GetMoviesResponse>

    /**
     * Get the list of the top rated movies
     */
    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<GetMoviesResponse>

    /**
     * Get the list of the upcoming movies
     */
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<GetMoviesResponse>

    /**
     * Get the list of the now playing movies
     */
    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<GetMoviesResponse>

}