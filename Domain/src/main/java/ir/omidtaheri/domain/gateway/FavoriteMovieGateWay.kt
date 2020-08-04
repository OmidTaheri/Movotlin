package ir.omidtaheri.domain.gateway


import io.reactivex.Completable
import io.reactivex.Flowable
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity

interface FavoriteMovieGateWay {
    fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Completable
    fun UnFavoriteMovie(MovieId: Int): Completable
    fun getFavoritedMovieList(): Flowable<DataState<List<FavoritedMovieDomainEntity>>>
}
