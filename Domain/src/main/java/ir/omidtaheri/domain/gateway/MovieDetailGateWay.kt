package ir.omidtaheri.domain.gateway

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.domain.entity.MovieEntity

interface MovieDetailGateWay {
    fun GetMovieDetail(MovieId: Long): Single<MovieEntity>

}
