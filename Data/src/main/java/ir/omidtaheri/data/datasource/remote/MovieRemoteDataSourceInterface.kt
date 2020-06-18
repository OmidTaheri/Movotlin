package ir.omidtaheri.data.datasource.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.MovieDataEntity

interface MovieRemoteDataSourceInterface {
    fun GetMovies(): Single<List<MovieDataEntity>>
}
