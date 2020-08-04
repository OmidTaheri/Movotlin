package ir.omidtaheri.data.repository

import androidx.lifecycle.Transformations.map
import io.reactivex.Completable
import io.reactivex.Flowable
import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.mapper.FavoritedMovieEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    val movieLocalDataSource: MovieLocalDataSourceInterface,
    val favoritedMovieEntityDomainDataMapper: FavoritedMovieEntityDomainDataMapper

) : FavoriteMovieGateWay {

    override fun FavoriteMovie(Movie: FavoritedMovieDomainEntity): Completable {
        return movieLocalDataSource.FavoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                Movie
            )
        )
    }

    override fun UnFavoriteMovie(MovieId: Int): Completable {
        return movieLocalDataSource.UnFavoriteMovie(MovieId)
    }

    override fun getFavoritedMovieList(): Flowable<DataState<List<FavoritedMovieDomainEntity>>> {

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


