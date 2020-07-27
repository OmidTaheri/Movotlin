package ir.omidtaheri.local.datasource

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.local.dao.MovieDao

import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import javax.inject.Inject

class MovieLocalDataSourceImp @Inject constructor(
    val movieDao: MovieDao,
    val movieEntityDataLocalMapper: MovieEntityDataLocalMapper
) : MovieLocalDataSourceInterface {


    override fun FavoriteMovie(Movie: FavoritedMovieDataEntity): Completable {
        movieDao.FavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(Movie))
        return Completable.complete()
    }

    override fun UnFavoriteMovie(MovieId: Int): Completable {
        movieDao.UnFavoriteMovie(MovieId)
        return Completable.complete()
    }

    override fun GetFavoritedMoviesList(): Flowable<List<FavoritedMovieDataEntity>> {
        return movieDao.GetFavoritedMoviesList().map {
            it.map {
                movieEntityDataLocalMapper.mapToDataEntity(it)
            }

        }
    }

}