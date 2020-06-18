package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.ReviewEntity

interface MovieReviewsGateWay {
    fun GetReviews(MovieId: Long): Single<List<ReviewEntity>>

}