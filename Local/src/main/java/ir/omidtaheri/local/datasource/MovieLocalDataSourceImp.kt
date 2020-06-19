package ir.omidtaheri.local.datasource

import io.reactivex.rxjava3.core.Completable
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.local.dao.MovieDao

import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper

class MovieLocalDataSourceImp(
    val movieDao: MovieDao,
    val movieEntityDataLocalMapper: MovieEntityDataLocalMapper
) : MovieLocalDataSourceInterface {

    override fun FavoriteMovie(Movie: MovieDataEntity): Completable {
        movieDao.FavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(Movie))
        return Completable.complete()
    }

    override fun UnFavoriteMovie(Movie: MovieDataEntity): Completable {
        movieDao.UnFavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(Movie))
        return Completable.complete()
    }

}