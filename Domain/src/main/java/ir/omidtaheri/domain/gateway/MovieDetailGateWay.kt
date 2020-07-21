package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity


interface MovieDetailGateWay {
    fun GetMovieDetail(MovieId: Long): Single<DataState<MovieDetailDomainEntity>>

}
