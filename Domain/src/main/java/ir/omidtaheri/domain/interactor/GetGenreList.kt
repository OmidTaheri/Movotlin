package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetGenreList @Inject constructor(schedulers: Schedulers, val movieRepository: MovieGateWay) :
    SingleUseCase<Unit, DataState<List<GenreDomainEntity>>>(schedulers) {

    override fun buildSingle(params: Unit): Single<DataState<List<GenreDomainEntity>>> {

        return movieRepository.GetGenreList()
    }

}