package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity

interface MovieRemoteDataSourceInterface {
    fun GetTopRatedMovies(page: Int): Single<MultiMovieDataEntity>
    fun GetPopularMovies(page: Int): Single<MultiMovieDataEntity>
    fun GetGenreList(): Single<List<GenreDataEntity>>
    fun GetUpComingMovies(page: Int): Single<MultiMovieDataEntity>
}
