package ir.omidtaheri.data.datasource.local

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSourceInterface {
    suspend fun favoriteMovie(movie: FavoritedMovieDataEntity): Long
    suspend fun unFavoriteMovie(movie: FavoritedMovieDataEntity): Long
    fun getFavoritedMoviesList(): Flow<List<FavoritedMovieDataEntity>>
}
