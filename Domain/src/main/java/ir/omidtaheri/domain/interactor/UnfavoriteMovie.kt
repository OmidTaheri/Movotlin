package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.interactor.base.SuspendUseCase
import javax.inject.Inject

class UnfavoriteMovie @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieGateWay
) :
    SuspendUseCase<FavoritedMovieDomainEntity, Long>() {

    override suspend fun buildSingle(params: FavoritedMovieDomainEntity): Long {
        return favoriteMovieRepository.unFavoriteMovie(params)
    }
}
