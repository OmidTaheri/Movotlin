package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    schedulers: Schedulers,
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    SingleUseCase<Int, DataState<MovieDetailDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MovieDetailDomainEntity>> {
        return discoverMovieRepository.getMovieDetailById(params)
    }
}
