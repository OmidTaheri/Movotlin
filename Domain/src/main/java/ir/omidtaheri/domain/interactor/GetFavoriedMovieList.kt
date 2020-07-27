package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.*
import javax.inject.Inject

class GetFavoriedMovieList @Inject constructor(schedulers: Schedulers, val favoriteMovieRepository: FavoriteMovieGateWay) :
    FlowableUseCase<Unit, DataState<List<FavoritedMovieDomainEntity>>>(schedulers) {

    override fun buildSingle(params: Unit): Flowable<DataState<List<FavoritedMovieDomainEntity>>> {
       return  favoriteMovieRepository.getFavoritedMovieList()
    }


}