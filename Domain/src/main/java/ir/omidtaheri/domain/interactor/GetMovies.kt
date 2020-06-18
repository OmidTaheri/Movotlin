package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.MovieEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase

class GetMovies(schedulers: Schedulers, val movieRepository: MovieGateWay) :
    SingleUseCase<Unit, List<MovieEntity>>(schedulers) {

    override fun buildSingle(params: Unit?): Single<List<MovieEntity>> {

        return movieRepository.GetMovies()
    }
}