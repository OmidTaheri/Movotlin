package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesWithoutPaging @Inject constructor(
    private val movieRepository: MovieGateWay
) :
    FlowUseCase<Int, DataState<MultiMovieDomainEntity>>() {

    override fun buildSingle(params: Int): Flow<DataState<MultiMovieDomainEntity>> {

        return movieRepository.getTopRatedMovies(params)
    }
}
