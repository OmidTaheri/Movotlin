package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity

interface MovieGateWay {
    fun getTopRatedMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>
    fun getPopularMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>
    fun getGenreList(): Single<DataState<List<GenreDomainEntity>>>
    fun getUpComingMovies(page: Int): Single<DataState<MultiMovieDomainEntity>>
}
