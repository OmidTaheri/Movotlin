package ir.omidtaheri.remote.service

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.remote.dto.response.GetMoviesResponse
import ir.omidtaheri.remote.dto.response.GetReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieReviewApi {



    /**
     * Get list of reviews
     */
    @GET("movie/{movieId}/reviews")
    fun getMovieReviews(@Path("movieId") movieId: Long): Single<GetReviewResponse>
}