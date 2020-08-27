package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity

interface MovieRemoteDataSourceInterface {
    fun getTopRatedMovies(page: Int): Single<MultiMovieDataEntity>
    fun getPopularMovies(page: Int): Single<MultiMovieDataEntity>
    fun getGenreList(): Single<List<GenreDataEntity>>
    fun getUpComingMovies(page: Int): Single<MultiMovieDataEntity>
}
