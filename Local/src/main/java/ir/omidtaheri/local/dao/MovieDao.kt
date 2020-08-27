package ir.omidtaheri.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.local.entity.MovieLocalEntity

@Dao
interface MovieDao {

    @Insert()
    fun favoriteMovie(movie: MovieLocalEntity): Single<Long>

    @Delete
    fun unFavoriteMovie(movie: MovieLocalEntity): Single<Int>

    @Query("SELECT * FROM movie")
    fun getFavoritedMoviesList(): Observable<List<MovieLocalEntity>>
}
