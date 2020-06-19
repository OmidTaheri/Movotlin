package ir.omidtaheri.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import ir.omidtaheri.local.entity.MovieLocalEntity

@Dao
interface MovieDao {

    @Insert(onConflict = IGNORE)
    fun FavoriteMovie(movie: MovieLocalEntity)

    @Delete
    fun UnFavoriteMovie(movie: MovieLocalEntity)

}