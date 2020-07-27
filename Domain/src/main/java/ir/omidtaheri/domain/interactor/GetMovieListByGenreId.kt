package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieListByGenreId  @Inject constructor(schedulers: Schedulers, val discoverMovieRepository: DiscoverMovieGateWay) :
    SingleUseCase<Map<String, Any>, DataState<MultiMovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: Map<String, Any>): Single<DataState<MultiMovieDomainEntity>> {
        return discoverMovieRepository.GetMovieListByGenreId(params)

    }
}