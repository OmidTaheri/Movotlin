package ir.omidtaheri.domain.gateway

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieGateWay {
    suspend fun favoriteMovie(movie: FavoritedMovieDomainEntity): Long
    suspend fun unFavoriteMovie(movie: FavoritedMovieDomainEntity): Long
    fun getFavoritedMovieList(): Flow<DataState<List<FavoritedMovieDomainEntity>>>
}
