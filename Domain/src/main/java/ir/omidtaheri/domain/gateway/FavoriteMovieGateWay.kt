package ir.omidtaheri.domain.gateway

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity

interface FavoriteMovieGateWay {
    fun favoriteMovie(movie: FavoritedMovieDomainEntity): Single<Long>
    fun unFavoriteMovie(movie: FavoritedMovieDomainEntity): Single<Int>
    fun getFavoritedMovieList(): Observable<DataState<List<FavoritedMovieDomainEntity>>>
    fun getFavoritedMoviesListByFlowable(): Flowable<DataState<List<FavoritedMovieDomainEntity>>>
}
