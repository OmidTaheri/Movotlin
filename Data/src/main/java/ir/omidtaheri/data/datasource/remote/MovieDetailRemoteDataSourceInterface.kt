package ir.omidtaheri.data.datasource.remote


import io.reactivex.Single
import ir.omidtaheri.data.entity.*

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity


interface MovieDetailRemoteDataSourceInterface {

    fun GetMovieDetailById(MovieId: Int): Single<MovieDetailDataEntity>
    fun GetMovieListByGenreId(Params: Map<String, Any>): Single<MultiMovieDataEntity>
    fun GetMovieImagesById(MovieId: Int): Single<MovieImageDataEntity>
    fun GetMovieVideosById(MovieId: Int): Single<MovieVideoDataEntity>
    fun GetSimilarMovieById(MovieId: Int,page:Int): Single<MultiMovieDataEntity>
    fun SearchMovieByQuery(query: String,page:Int): Single<MultiMovieDataEntity>

}
