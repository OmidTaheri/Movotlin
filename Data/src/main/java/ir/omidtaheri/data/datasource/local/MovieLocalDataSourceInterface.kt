package ir.omidtaheri.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.entity.MovieDataEntity

interface MovieLocalDataSourceInterface {
    fun FavoriteMovie(MovieId: Long): Completable
    fun UnFavoriteMovie(MovieId: Long): Completable

}
