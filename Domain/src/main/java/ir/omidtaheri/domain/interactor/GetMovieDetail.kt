package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    FlowUseCase<Int, DataState<MovieDetailDomainEntity>>() {

    override fun buildSingle(params: Int): Flow<DataState<MovieDetailDomainEntity>> {
        return discoverMovieRepository.getMovieDetailById(params)
    }
}
