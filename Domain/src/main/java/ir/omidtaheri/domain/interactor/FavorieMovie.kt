package ir.omidtaheri.domain.interactor

import io.reactivex.rxjava3.core.Completable
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.CompletableUseCase
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import javax.inject.Inject

class FavorieMovie @Inject constructor(schedulers: Schedulers, val favoriteMovieRepository: FavoriteMovieGateWay) :
    CompletableUseCase<FavoritedMovieDomainEntity>(schedulers) {


    override fun buildCompletable(params: FavoritedMovieDomainEntity): Completable {
        return favoriteMovieRepository.FavoriteMovie(params)
    }


}