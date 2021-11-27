package ir.omidtaheri.local.datasource

import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSourceImp @Inject constructor(
    private val movieDao: MovieDao,
    private val movieEntityDataLocalMapper: MovieEntityDataLocalMapper
) : MovieLocalDataSourceInterface {

    override suspend fun favoriteMovie(movie: FavoritedMovieDataEntity): Long {
        return movieDao.favoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(movie))
    }

    override suspend fun unFavoriteMovie(movie: FavoritedMovieDataEntity): Long {
        return movieDao.unFavoriteMovie(movieEntityDataLocalMapper.mapFromDataEntity(movie))
    }

    override fun getFavoritedMoviesList(): Flow<List<FavoritedMovieDataEntity>> {
        return movieDao.getFavoritedMoviesList().map {
            it.map {
                movieEntityDataLocalMapper.mapToDataEntity(it)
            }
        }
    }


}
