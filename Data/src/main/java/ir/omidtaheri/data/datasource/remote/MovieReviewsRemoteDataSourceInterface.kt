package ir.omidtaheri.data.datasource.remote

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.ReviewDataEntity

interface MovieReviewsRemoteDataSourceInterface {
    fun GetReviews(MovieId: Long): Single<List<ReviewDataEntity>>

}
