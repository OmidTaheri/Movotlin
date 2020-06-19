package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.ReviewEntityDomainDataMapper
import ir.omidtaheri.domain.entity.ReviewDomainEntity
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay

class MovieReviewsRepository(
    val movieReviewsRemoteDataSource: MovieReviewsRemoteDataSourceInterface,
    val reviewsEntityMapper: ReviewEntityDomainDataMapper

) : MovieReviewsGateWay {

    override fun GetReviews(MovieId: Long): Single<List<ReviewDomainEntity>> {

        return movieReviewsRemoteDataSource.GetReviews(MovieId).map {
            it.map {
                reviewsEntityMapper.mapFromDataEntity(it)
            }
        }

    }
}