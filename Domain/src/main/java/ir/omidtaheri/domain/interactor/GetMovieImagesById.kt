package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieImagesById  @Inject constructor(schedulers: Schedulers, val discoverMovieRepository: DiscoverMovieGateWay) :
    SingleUseCase<Int, DataState<MovieImageDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MovieImageDomainEntity>> {
        return discoverMovieRepository.GetMovieImagesById(params)

    }
}