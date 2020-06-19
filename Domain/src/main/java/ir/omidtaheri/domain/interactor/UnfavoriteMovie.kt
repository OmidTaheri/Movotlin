package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Completable
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.CompletableUseCase
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase

class UnfavoriteMovie(schedulers: Schedulers, val movieRepository: MovieGateWay) :
    CompletableUseCase<MovieDomainEntity>(schedulers) {
    override fun buildCompletable(params: MovieDomainEntity?): Completable {
        if (params == null) throw MissingUseCaseParamsException("Parameter not found")
        return movieRepository.UnFavoriteMovie(params)

    }
}