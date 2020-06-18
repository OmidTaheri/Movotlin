package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.MovieEntity
import ir.omidtaheri.domain.entity.ReviewEntity
import ir.omidtaheri.domain.gateway.MovieDetailGateWay
import ir.omidtaheri.domain.gateway.MovieReviewsGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase

class GetReviews(schedulers: Schedulers, val movieReviewsRepository: MovieReviewsGateWay) :
    SingleUseCase<Long, List<ReviewEntity>>(schedulers) {
    override fun buildSingle(params: Long?): Single<List<ReviewEntity>> {
        if (params == null) throw  MissingUseCaseParamsException("Parameter not found")
        return movieReviewsRepository.GetReviews(params)
    }
}