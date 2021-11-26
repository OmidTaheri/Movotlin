package ir.omidtaheri.domain.gateway

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams
import kotlinx.coroutines.flow.Flow

interface DiscoverMovieGateWay {
    fun getMovieDetailById(movieId: Int): Flow<DataState<MovieDetailDomainEntity>>
    fun getMovieListByGenreId(params: Map<String, Int>): Flow<DataState<MultiMovieDomainEntity>>
    fun getMovieImagesById(movieId: Int): Flow<DataState<MovieImageDomainEntity>>
    fun getMovieVideosById(movieId: Int): Flow<DataState<MovieVideoDomainEntity>>
    fun getSimilarMovieById(params: GetSimilarMoviesParams): Flow<DataState<MultiMovieDomainEntity>>
    fun searchMovieByQuery(params: SearchMovieByQueryParams): Flow<DataState<MultiMovieDomainEntity>>
}
