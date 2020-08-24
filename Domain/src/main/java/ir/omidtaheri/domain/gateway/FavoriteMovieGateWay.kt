package ir.omidtaheri.domain.gateway


import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity

interface FavoriteMovieGateWay {
    fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Long>
    fun UnFavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Int>
    fun getFavoritedMovieList(): Observable<DataState<List<FavoritedMovieDomainEntity>>>
}
