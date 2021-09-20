package ir.omidtaheri.domain.interactor

import io.reactivex.Flowable
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowableUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class GetFavoriedMovieListByFlowable @Inject constructor(
    schedulers: Schedulers,
    private val favoriteMovieRepository: FavoriteMovieGateWay
) :
    FlowableUseCase<Unit, DataState<List<FavoritedMovieDomainEntity>>>(schedulers) {

    override fun buildSingle(params: Unit): Flowable<DataState<List<FavoritedMovieDomainEntity>>> {
        return favoriteMovieRepository.getFavoritedMoviesListByFlowable()
    }
}
