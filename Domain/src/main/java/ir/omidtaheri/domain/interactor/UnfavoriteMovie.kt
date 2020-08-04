package ir.omidtaheri.domain.interactor


import io.reactivex.Completable
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
    CompletableUseCase<Int>(schedulers) {
    override fun buildCompletable(params: Int): Completable {
        return favoriteMovieRepository.UnFavoriteMovie(params)

    }


}