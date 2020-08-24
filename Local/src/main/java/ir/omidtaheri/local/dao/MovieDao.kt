package ir.omidtaheri.local.dao


import androidx.room.*
import io.reactivex.Completable
import ir.omidtaheri.local.entity.MovieLocalEntity
import java.util.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert()
    fun FavoriteMovie(movie: MovieLocalEntity): Single<Long>

    @Delete
    fun UnFavoriteMovie(movie: MovieLocalEntity): Single<Int>

    @Query("SELECT * FROM movie")
    fun GetFavoritedMoviesList(): Observable<List<MovieLocalEntity>>

}