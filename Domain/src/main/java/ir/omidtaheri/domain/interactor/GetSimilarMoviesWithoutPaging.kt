package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMoviesWithoutPaging @Inject constructor(
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    FlowUseCase<GetSimilarMoviesParams, DataState<MultiMovieDomainEntity>>() {

    override fun buildSingle(params: GetSimilarMoviesParams): Flow<DataState<MultiMovieDomainEntity>> {
        return discoverMovieRepository.getSimilarMovieById(params)
    }
}
