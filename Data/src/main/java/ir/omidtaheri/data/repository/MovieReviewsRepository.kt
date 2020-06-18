package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.ReviewEntityMapper
import ir.omidtaheri.domain.entity.ReviewEntity
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay

class MovieReviewsRepository(
    val movieReviewsRemoteDataSource: MovieReviewsRemoteDataSourceInterface,
    val reviewsEntityMapper: ReviewEntityMapper

) : MovieReviewsGateWay {

    override fun GetReviews(MovieId: Long): Single<List<ReviewEntity>> {

        return movieReviewsRemoteDataSource.GetReviews(MovieId).map {
            it.map {
                reviewsEntityMapper.mapFromDataEntity(it)
            }
        }

    }
}