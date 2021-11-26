package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosById @Inject constructor(
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    FlowUseCase<Int, DataState<MovieVideoDomainEntity>>() {

    override fun buildSingle(params: Int): Flow<DataState<MovieVideoDomainEntity>> {
        if (params == null) throw MissingUseCaseParamsException("Parameter not found")
        return discoverMovieRepository.getMovieVideosById(params)
    }
}
