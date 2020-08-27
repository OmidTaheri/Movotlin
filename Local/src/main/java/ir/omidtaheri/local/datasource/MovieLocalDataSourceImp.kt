package ir.omidtaheri.local.datasource

import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import javax.inject.Inject

class MovieLocalDataSourceImp @Inject constructor(
    val movieDao: MovieDao,
    val movieEntityDataLocalMapper: MovieEntityDataLocalMapper
) : MovieLocalDataSourceInterface {

    override fun FavoriteMovie(Movie: FavoritedMovieDataEntity): Single<Long> {
        return movieDao.FavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(Movie))
    }

    override fun UnFavoriteMovie(Movie: FavoritedMovieDataEntity): Single<Int> {
        return movieDao.UnFavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(Movie))
    }

    override fun GetFavoritedMoviesList(): Observable<List<FavoritedMovieDataEntity>> {
        return movieDao.GetFavoritedMoviesList().map {
            it.map {
                movieEntityDataLocalMapper.mapToDataEntity(it)
            }
        }
    }
}
