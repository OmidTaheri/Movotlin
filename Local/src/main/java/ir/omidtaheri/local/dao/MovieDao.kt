package ir.omidtaheri.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ir.omidtaheri.local.entity.MovieLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert()
    suspend fun favoriteMovie(movie: MovieLocalEntity): Long

    @Delete
    suspend fun unFavoriteMovie(movie: MovieLocalEntity): Int

    @Query("SELECT * FROM movie")
    fun getFavoritedMoviesList(): Flow<List<MovieLocalEntity>>

}
