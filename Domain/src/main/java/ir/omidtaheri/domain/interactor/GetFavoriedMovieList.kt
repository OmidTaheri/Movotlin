package ir.omidtaheri.domain.interactor

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriedMovieList @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieGateWay
) :
    FlowUseCase<Unit, DataState<List<FavoritedMovieDomainEntity>>>() {

    override fun buildSingle(params: Unit): Flow<DataState<List<FavoritedMovieDomainEntity>>> {
        return favoriteMovieRepository.getFavoritedMovieList()
    }
}
