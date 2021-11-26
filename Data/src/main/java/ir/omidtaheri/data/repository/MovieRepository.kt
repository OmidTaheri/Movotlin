package ir.omidtaheri.data.repository

import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.GenreEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSourceInterface,
    private val multiMovieEntityDomainDataMapper: MultiMovieEntityDomainDataMapper,
    private val genreEntityDomainDataMapper: GenreEntityDomainDataMapper
) : MovieGateWay {

    override fun getTopRatedMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>> {

        return movieRemoteDataSource.getTopRatedMovies(page).map {

            val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

            DataState.SUCCESS(
                multiMovieDomain,
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )


        }.catch<DataState<MultiMovieDomainEntity>> {
            emit(
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            )
        }

    }

    override fun getPopularMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.getPopularMovies(page)
            .map {

                val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

                DataState.SUCCESS(
                    multiMovieDomain,
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }.catch<DataState<MultiMovieDomainEntity>> {
                emit(
                    DataState.ERROR(
                        StateMessage(
                            MessageHolder.MESSAGE(it.message ?: "Error"),
                            UiComponentType.SNACKBAR,
                            MessageType.ERROR
                        )
                    )
                )
            }
    }

    override fun getGenreList(): Flow<DataState<List<GenreDomainEntity>>> {
        return movieRemoteDataSource.getGenreList().map {

            val genreListDomainEntity = it.map {
                genreEntityDomainDataMapper.mapFromDataEntity(it)
            }

            DataState.SUCCESS(
                genreListDomainEntity,
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }
            .catch<DataState<List<GenreDomainEntity>>> {
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

    override fun getUpComingMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.getUpComingMovies(page)
            .map {

                val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

                DataState.SUCCESS(
                    multiMovieDomain,
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }.catch<DataState<MultiMovieDomainEntity>> {
                emit(
                    DataState.ERROR(
                        StateMessage(
                            MessageHolder.MESSAGE(it.message ?: "Error"),
                            UiComponentType.SNACKBAR,
                            MessageType.ERROR
                        )
                    )
                )
            }
    }
}
