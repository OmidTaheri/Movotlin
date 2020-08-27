package ir.omidtaheri.data.datasource.local

import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity

interface MovieLocalDataSourceInterface {
    fun FavoriteMovie(Movie: FavoritedMovieDataEntity): Single<Long>
    fun UnFavoriteMovie(Movie: FavoritedMovieDataEntity): Single<Int>
    fun GetFavoritedMoviesList(): Observable<List<FavoritedMovieDataEntity>>
}
