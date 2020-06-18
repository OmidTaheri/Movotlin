package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.MovieEntity

interface MovieGateWay {
    fun FavoriteMovie(MovieId: Long): Completable
    fun UnFavoriteMovie(MovieId: Long): Completable
    fun GetMovies(): Single<List<MovieEntity>>

}
