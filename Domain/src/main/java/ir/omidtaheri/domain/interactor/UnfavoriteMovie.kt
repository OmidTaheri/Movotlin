package ir.omidtaheri.domain.interactor


import io.reactivex.Completable
import io.reactivex.Single
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.CompletableUseCase
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class UnfavoriteMovie @Inject constructor(
    schedulers: Schedulers,
    val favoriteMovieRepository: FavoriteMovieGateWay
) :
    SingleUseCase<FavoritedMovieDomainEntity, Int>(schedulers) {


    override fun buildSingle(params: FavoritedMovieDomainEntity): Single<Int> {
        return favoriteMovieRepository.UnFavoriteMovie(params)
    }


}