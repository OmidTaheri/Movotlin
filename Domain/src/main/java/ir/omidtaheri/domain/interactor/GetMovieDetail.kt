package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.MovieEntity
import ir.omidtaheri.domain.gateway.MovieDetailGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase

class GetMovieDetail(schedulers: Schedulers, val movieDetailRepository: MovieDetailGateWay) :
    SingleUseCase<Long, MovieEntity>(schedulers) {

    override fun buildSingle(params: Long?): Single<MovieEntity> {
        if (params == null) throw  MissingUseCaseParamsException("Parameter not found")
        return movieDetailRepository.GetMovieDetail(params)

    }
}