package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class FavorieMovie @Inject constructor(
    schedulers: Schedulers,
    val favoriteMovieRepository: FavoriteMovieGateWay
) :
    SingleUseCase<FavoritedMovieDomainEntity, Long>(schedulers) {

    override fun buildSingle(params: FavoritedMovieDomainEntity): Single<Long> {
        return favoriteMovieRepository.FavoriteMovie(params)
    }
}
