package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.data.mapper.MovieEntityDomainDataMapper
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import javax.inject.Inject

class MovieRepository  @Inject constructor(
    val movieRemoteDataSource: MovieRemoteDataSourceInterface,
    val movieLocalDataSource: MovieLocalDataSourceInterface,
    val movieEntityMapper: MovieEntityDomainDataMapper
) : MovieGateWay {

    override fun FavoriteMovie(Movie: MovieDomainEntity): Completable {

        return movieLocalDataSource.FavoriteMovie(movieEntityMapper.mapToDataEntity(Movie))
    }

    override fun UnFavoriteMovie(Movie: MovieDomainEntity): Completable {

        return movieLocalDataSource.UnFavoriteMovie(movieEntityMapper.mapToDataEntity(Movie))
    }

    override fun GetMovies(): Single<List<MovieDomainEntity>> {

        return movieRemoteDataSource.GetMovies().map {
            it.map {
                movieEntityMapper.mapFromDataEntity(it)
            }
        }
    }
}