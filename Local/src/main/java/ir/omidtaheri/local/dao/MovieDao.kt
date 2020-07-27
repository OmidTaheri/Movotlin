package ir.omidtaheri.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.local.entity.MovieLocalEntity
import java.util.*

@Dao
interface MovieDao {

    @Insert(onConflict = IGNORE)
    fun FavoriteMovie(movie: MovieLocalEntity)

    @Delete
    fun UnFavoriteMovie(movieId: Int)

    @Query("SELECT * FROM Movie")
    fun GetFavoritedMoviesList(): Flowable<List<MovieLocalEntity>>
}