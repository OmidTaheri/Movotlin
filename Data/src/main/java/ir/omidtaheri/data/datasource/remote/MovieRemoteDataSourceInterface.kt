package ir.omidtaheri.data.datasource.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity

interface MovieRemoteDataSourceInterface {
    fun GetTopRatedMovies(page:Int): Single<MultiMovieDataEntity>
    fun GetPopularMovies(page:Int): Single<MultiMovieDataEntity>
    fun GetGenreList(): Single<List<GenreDataEntity>>
    fun GetUpComingMovies(page:Int): Single<MultiMovieDataEntity>
}
