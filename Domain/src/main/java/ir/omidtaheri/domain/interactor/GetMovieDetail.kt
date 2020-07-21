package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.gateway.MovieDetailGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetMovieDetail  @Inject constructor(schedulers: Schedulers, val movieDetailRepository: MovieDetailGateWay) :
    SingleUseCase<Long, DataState<MovieDetailDomainEntity>>(schedulers) {

    override fun buildSingle(params: Long?): Single<DataState<MovieDetailDomainEntity>> {
        if (params == null) throw  MissingUseCaseParamsException("Parameter not found")
        return movieDetailRepository.GetMovieDetail(params)

    }
}