package ir.omidtaheri.local.datasource

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import javax.inject.Inject

class MovieLocalDataSourceImp @Inject constructor(
    private val movieDao: MovieDao,
    private val movieEntityDataLocalMapper: MovieEntityDataLocalMapper
) : MovieLocalDataSourceInterface {

    override fun favoriteMovie(movie: FavoritedMovieDataEntity): Single<Long> {
        return movieDao.favoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(movie))
    }

    override fun unFavoriteMovie(movie: FavoritedMovieDataEntity): Single<Int> {
        return movieDao.unFavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(movie))
    }

    override fun getFavoritedMoviesList(): Observable<List<FavoritedMovieDataEntity>> {
        return movieDao.getFavoritedMoviesList().map {
            it.map {
                movieEntityDataLocalMapper.mapToDataEntity(it)
            }
        }
    }

    override fun getFavoritedMoviesListByFlowable(): Flowable<List<FavoritedMovieDataEntity>> {
        return movieDao.getFavoritedMoviesListByFlowable().map {
            it.map {
                movieEntityDataLocalMapper.mapToDataEntity(it)
            }
        }
    }

}
