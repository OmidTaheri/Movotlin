package ir.omidtaheri.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.data.entity.MovieDataEntity

interface MovieLocalDataSourceInterface {
    fun FavoriteMovie(Movie: FavoritedMovieDataEntity): Completable
    fun UnFavoriteMovie(MovieId: Int): Completable
    fun GetFavoritedMoviesList(): Flowable<List<FavoritedMovieDataEntity>>
}
