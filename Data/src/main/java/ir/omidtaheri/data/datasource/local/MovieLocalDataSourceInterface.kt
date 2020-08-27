package ir.omidtaheri.data.datasource.local

import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity

interface MovieLocalDataSourceInterface {
    fun favoriteMovie(movie: FavoritedMovieDataEntity): Single<Long>
    fun unFavoriteMovie(movie: FavoritedMovieDataEntity): Single<Int>
    fun getFavoritedMoviesList(): Observable<List<FavoritedMovieDataEntity>>
}
