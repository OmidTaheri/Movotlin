package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieImagesById @Inject constructor(
    schedulers: Schedulers,
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    SingleUseCase<Int, DataState<MovieImageDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MovieImageDomainEntity>> {
        return discoverMovieRepository.getMovieImagesById(params)
    }
}
