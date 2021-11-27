package ir.omidtaheri.data.repository

import ir.omidtaheri.data.datasource.local.MovieLocalDataSourceInterface
import ir.omidtaheri.data.mapper.FavoritedMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.domain.gateway.FavoriteMovieGateWay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSourceInterface,
    private val favoritedMovieEntityDomainDataMapper: FavoritedMovieEntityDomainDataMapper

) : FavoriteMovieGateWay {

    override suspend fun favoriteMovie(movie: FavoritedMovieDomainEntity): Long {
        return movieLocalDataSource.favoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                movie
            )
        )
    }

    override suspend fun unFavoriteMovie(movie: FavoritedMovieDomainEntity): Int {
        return movieLocalDataSource.unFavoriteMovie(
            favoritedMovieEntityDomainDataMapper.mapToDataEntity(
                movie
            )
        )
    }

    override fun getFavoritedMovieList(): Flow<DataState<List<FavoritedMovieDomainEntity>>> {

        return movieLocalDataSource.getFavoritedMoviesList().map {

            DataState.SUCCESS(
                it.map {
                    favoritedMovieEntityDomainDataMapper.mapFromDataEntity(it)
                },
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }.catch<DataState<List<FavoritedMovieDomainEntity>>> {
            emit(
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.DIALOG,
                        MessageType.ERROR
                    )
                )
            )
        }
    }

}
