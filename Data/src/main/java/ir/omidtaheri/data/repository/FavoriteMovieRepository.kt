package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieReviewsRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.ReviewEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.ReviewDomainEntity
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay
import javax.inject.Inject

class MovieReviewsRepository @Inject constructor(
    val movieReviewsRemoteDataSource: MovieReviewsRemoteDataSourceInterface,
    val reviewsEntityMapper: ReviewEntityDomainDataMapper

) : MovieReviewsGateWay {

    override fun GetReviews(MovieId: Long): Single<DataState<List<ReviewDomainEntity>>> {

        return movieReviewsRemoteDataSource.GetReviews(MovieId)
            .map<DataState<List<ReviewDomainEntity>>> {

                val list = it.map {
                    reviewsEntityMapper.mapFromDataEntity(it)
                }

                DataState.SUCCESS(
                    list,
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )


            }
            .onErrorReturn {
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            }

    }

}
