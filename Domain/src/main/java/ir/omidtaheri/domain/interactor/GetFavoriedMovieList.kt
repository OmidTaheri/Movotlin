package ir.omidtaheri.domain.interactor

import io.reactivex.Observable
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.interactor.base.ObservableUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class GetFavoriedMovieList @Inject constructor(
    schedulers: Schedulers,
    val favoriteMovieRepository: FavoriteMovieGateWay
) :
    ObservableUseCase<Unit, DataState<List<FavoritedMovieDomainEntity>>>(schedulers) {

    override fun buildSingle(params: Unit): Observable<DataState<List<FavoritedMovieDomainEntity>>> {
        return favoriteMovieRepository.getFavoritedMovieList()
    }
}
