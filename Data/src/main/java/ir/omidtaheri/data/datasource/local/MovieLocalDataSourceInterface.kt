package ir.omidtaheri.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.MovieDataEntity

interface MovieLocalDataSourceInterface {
    fun FavoriteMovie(Movie : MovieDataEntity): Completable
    fun UnFavoriteMovie(Movie : MovieDataEntity): Completable

}
