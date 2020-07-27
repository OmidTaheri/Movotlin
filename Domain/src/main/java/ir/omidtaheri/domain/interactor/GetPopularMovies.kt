package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetPopularMovies  @Inject constructor(schedulers: Schedulers, val movieRepository: MovieGateWay) :
    SingleUseCase<Int, DataState<MultiMovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRepository.GetPopularMovies(params)
    }

}