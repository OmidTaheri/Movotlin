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
    fun GetMovieDetailById(MovieId: Int): Single<DataState<MovieDetailDomainEntity>>
    fun GetMovieListByGenreId(Params: Map<String, Any>): Single<DataState<MultiMovieDomainEntity>>
    fun GetMovieImagesById(MovieId: Int): Single<DataState<MovieImageDomainEntity>>
    fun GetMovieVideosById(MovieId: Int): Single<DataState<MovieVideoDomainEntity>>
    fun GetSimilarMovieById(Params: GetSimilarMoviesParams): Single<DataState<MultiMovieDomainEntity>>
    fun SearchMovieByQuery(params: SearchMovieByQueryParams): Single<DataState<MultiMovieDomainEntity>>
}
