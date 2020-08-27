package ir.omidtaheri.domain.gateway

import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity

interface FavoriteMovieGateWay {
    fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Long>
    fun UnFavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Int>
    fun getFavoritedMovieList(): Observable<DataState<List<FavoritedMovieDomainEntity>>>
}
