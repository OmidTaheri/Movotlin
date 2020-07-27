package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity

interface FavoriteMovieGateWay {
    fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Completable
    fun UnFavoriteMovie(MovieId: Int): Completable
    fun getFavoritedMovieList(): Flowable<DataState<List<FavoritedMovieDomainEntity>>>
}
