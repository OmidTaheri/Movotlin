package ir.omidtaheri.data.repository

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.mapper.FavoritedMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.MessageType
import ir.omidtaheri.domain.datastate.StateMessage
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSourceInterface,
    private val favoritedMovieEntityDomainDataMapper: FavoritedMovieEntityDomainDataMapper

) : FavoriteMovieGateWay {

    override fun favoriteMovie(movie: FavoritedMovieDomainEntity): Single<Long> {
        return movieLocalDataSource.favoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                movie
            )
        )
    }

    override fun unFavoriteMovie(movie: FavoritedMovieDomainEntity): Single<Int> {
        return movieLocalDataSource.unFavoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                movie
            )
        )
    }

    override fun getFavoritedMovieList(): Observable<DataState<List<FavoritedMovieDomainEntity>>> {

        return movieLocalDataSource.getFavoritedMoviesList()
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
                        UiComponentType.DIALOG,
                        MessageType.ERROR
                    )
                )
            }
    }


    override fun getFavoritedMoviesListByFlowable(): Flowable<DataState<List<FavoritedMovieDomainEntity>>> {

        return movieLocalDataSource.getFavoritedMoviesListByFlowable()
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
                        UiComponentType.DIALOG,
                        MessageType.ERROR
                    )
                )
            }
    }


}
