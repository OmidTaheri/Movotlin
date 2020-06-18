package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieEntityMapper
import ir.omidtaheri.domain.entity.MovieEntity
import ir.omidtaheri.domain.gateway.MovieGateWay

class MovieRepository(
    val movieRemoteDataSource: MovieRemoteDataSourceInterface,
    val movieLocalDataSource: MovieLocalDataSourceInterface,
    val movieEntityMapper: MovieEntityMapper
) : MovieGateWay {
    override fun FavoriteMovie(MovieId: Long): Completable {

        return movieLocalDataSource.FavoriteMovie(MovieId)
    }

    override fun UnFavoriteMovie(MovieId: Long): Completable {

        return movieLocalDataSource.UnFavoriteMovie(MovieId)
    }

    override fun GetMovies(): Single<List<MovieEntity>> {

        return movieRemoteDataSource.GetMovies().map {
            it.map {
                movieEntityMapper.mapFromDataEntity(it)
            }
        }
    }
}