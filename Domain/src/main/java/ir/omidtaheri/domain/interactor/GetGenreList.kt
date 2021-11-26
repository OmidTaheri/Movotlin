package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreList @Inject constructor(
    private val movieRepository: MovieGateWay
) :
    FlowUseCase<Unit, DataState<List<GenreDomainEntity>>>() {

    override fun buildSingle(params: Unit): Flow<DataState<List<GenreDomainEntity>>> {
        return movieRepository.getGenreList()
    }
}
