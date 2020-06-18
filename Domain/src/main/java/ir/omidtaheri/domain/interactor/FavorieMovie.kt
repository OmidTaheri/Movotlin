package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Completable
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.CompletableUseCase
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers

class FavorieMovie(schedulers: Schedulers, val movieRepository: MovieGateWay) :
    CompletableUseCase<Long>(schedulers) {


    override fun buildCompletable(params: Long?): Completable {

        if (params == null) throw MissingUseCaseParamsException("Parameter not found")
        return movieRepository.FavoriteMovie(params)
    }


}