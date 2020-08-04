package ir.omidtaheri.data.datasource.local

import io.reactivex.Completable
import io.reactivex.Flowable
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.data.entity.MovieDataEntity

interface MovieLocalDataSourceInterface {
    fun FavoriteMovie(Movie: FavoritedMovieDataEntity): Completable
    fun UnFavoriteMovie(MovieId: Int): Completable
    fun GetFavoritedMoviesList(): Flowable<List<FavoritedMovieDataEntity>>
}
