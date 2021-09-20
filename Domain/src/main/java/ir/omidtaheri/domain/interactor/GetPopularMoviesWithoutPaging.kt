package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class GetPopularMoviesWithoutPaging @Inject constructor(
    schedulers: Schedulers,
    private val movieRepository: MovieGateWay
) :
    SingleUseCase<Int, DataState<MultiMovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRepository.getPopularMovies(params)
    }
}
