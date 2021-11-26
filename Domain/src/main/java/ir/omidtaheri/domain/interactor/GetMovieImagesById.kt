package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieImagesById @Inject constructor(
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    FlowUseCase<Int, DataState<MovieImageDomainEntity>>() {

    override fun buildSingle(params: Int): Flow<DataState<MovieImageDomainEntity>> {
        return discoverMovieRepository.getMovieImagesById(params)
    }
}
