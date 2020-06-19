package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.ReviewDomainEntity
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase

class GetReviews(schedulers: Schedulers, val movieReviewsRepository: MovieReviewsGateWay) :
    SingleUseCase<Long, List<ReviewDomainEntity>>(schedulers) {
    override fun buildSingle(params: Long?): Single<List<ReviewDomainEntity>> {
        if (params == null) throw  MissingUseCaseParamsException("Parameter not found")
        return movieReviewsRepository.GetReviews(params)
    }
}