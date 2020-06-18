package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.dto.response.GetMovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {


    /**
     * Get the details of a specified movie
     */
    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: Long): Single<GetMovieDetailResponse>

}