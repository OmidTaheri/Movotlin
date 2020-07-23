package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDomainEntity

interface MovieGateWay {
    fun FavoriteMovie(Movie: MovieDomainEntity): Completable
    fun UnFavoriteMovie(Movie: MovieDomainEntity): Completable
    fun GetMovies(): Single<DataState<List<MovieDomainEntity>>>

}
