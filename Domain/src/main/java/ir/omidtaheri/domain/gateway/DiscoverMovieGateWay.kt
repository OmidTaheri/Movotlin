package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams

interface DiscoverMovieGateWay {
    fun getMovieDetailById(movieId: Int): Single<DataState<MovieDetailDomainEntity>>
    fun getMovieListByGenreId(params: Map<String, Int>): Single<DataState<MultiMovieDomainEntity>>
    fun getMovieImagesById(movieId: Int): Single<DataState<MovieImageDomainEntity>>
    fun getMovieVideosById(movieId: Int): Single<DataState<MovieVideoDomainEntity>>
    fun getSimilarMovieById(params: GetSimilarMoviesParams): Single<DataState<MultiMovieDomainEntity>>
    fun searchMovieByQuery(params: SearchMovieByQueryParams): Single<DataState<MultiMovieDomainEntity>>
}
