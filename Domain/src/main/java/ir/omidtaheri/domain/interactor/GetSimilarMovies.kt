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
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import javax.inject.Inject

class GetSimilarMovies  @Inject constructor(schedulers: Schedulers, val discoverMovieRepository: DiscoverMovieGateWay) :
    SingleUseCase<GetSimilarMoviesParams, DataState<MultiMovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetSimilarMoviesParams): Single<DataState<MultiMovieDomainEntity>> {
        return discoverMovieRepository.GetSimilarMovieById(params)

    }
}