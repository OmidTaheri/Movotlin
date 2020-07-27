package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.ReviewDomainEntity

interface MovieReviewsGateWay {
    fun GetReviews(MovieId: Long): Single<DataState<List<ReviewDomainEntity>>>

}
