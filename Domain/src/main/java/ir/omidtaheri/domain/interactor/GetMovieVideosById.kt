package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieVideosById @Inject constructor(
    schedulers: Schedulers,
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    SingleUseCase<Int, DataState<MovieVideoDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MovieVideoDomainEntity>> {
        if (params == null) throw MissingUseCaseParamsException("Parameter not found")
        return discoverMovieRepository.getMovieVideosById(params)
    }
}
