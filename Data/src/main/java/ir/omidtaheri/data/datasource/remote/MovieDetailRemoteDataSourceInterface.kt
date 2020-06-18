package ir.omidtaheri.data.datasource.remote

 import io.reactivex.rxjava3.core.Single

import ir.omidtaheri.data.entity.MovieDetailDataEntity



interface MovieDetailRemoteDataSourceInterface {
    fun GetMovieDetail(MovieId: Long): Single<MovieDetailDataEntity>

}
