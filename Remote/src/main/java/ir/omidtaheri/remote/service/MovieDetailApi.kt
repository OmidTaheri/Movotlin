package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.entity.response.MovieDetailResponseRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {


    /**
     * Get the details of a specified movie
     */
    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: Long): Single<MovieDetailResponseRemoteEntity>

}