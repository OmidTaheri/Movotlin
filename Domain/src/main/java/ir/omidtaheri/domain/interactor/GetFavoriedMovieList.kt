package ir.omidtaheri.domain.interactor


import io.reactivex.Flowable
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