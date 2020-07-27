package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity

interface MovieGateWay {
    fun GetTopRatedMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>
    fun GetPopularMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>
    fun GetGenreList(): Single<DataState<List<GenreDomainEntity>>>
    fun GetUpComingMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>

}
