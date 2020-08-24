package ir.omidtaheri.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.mapper.FavoritedMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    val movieLocalDataSource: MovieLocalDataSourceInterface,
    val favoritedMovieEntityDomainDataMapper: FavoritedMovieEntityDomainDataMapper

) : FavoriteMovieGateWay {

    override fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Long> {
        return movieLocalDataSource.FavoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                Movie
            )
        )
    }

    override fun UnFavoriteMovie(Movie: FavoritedMovieDomainEntity): Single<Int> {
        return movieLocalDataSource.UnFavoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                Movie
            )
        )
    }

    override fun getFavoritedMovieList(): Observable<DataState<List<FavoritedMovieDomainEntity>>> {

        return movieLocalDataSource.GetFavoritedMoviesList()
            .map<DataState<List<FavoritedMovieDomainEntity>>> {

                DataState.SUCCESS(
                    it.map {
                        favoritedMovieEntityDomainDataMapper.mapFromDataEntity(it)
                    },
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )

            }.onErrorReturn {
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            }
    }


}


