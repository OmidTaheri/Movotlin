package ir.omidtaheri.data.datasource.remote

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSourceInterface {
    fun getTopRatedMovies(page: Int): Flow<MultiMovieDataEntity>
    fun getPopularMovies(page: Int): Flow<MultiMovieDataEntity>
    fun getGenreList(): Flow<List<GenreDataEntity>>
    fun getUpComingMovies(page: Int): Flow<MultiMovieDataEntity>
}
