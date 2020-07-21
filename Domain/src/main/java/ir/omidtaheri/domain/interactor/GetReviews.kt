package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.ReviewDomainEntity
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetReviews @Inject constructor(
    schedulers: Schedulers,
    val movieReviewsRepository: MovieReviewsGateWay
) :
    SingleUseCase<Long, DataState<List<ReviewDomainEntity>>>(schedulers) {
    override fun buildSingle(params: Long?): Single<DataState<List<ReviewDomainEntity>>> {
        if (params == null) throw  MissingUseCaseParamsException("Parameter not found")
        return movieReviewsRepository.GetReviews(params)
    }
}