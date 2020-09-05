package ir.omidtaheri.data.repository

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.GenreEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.MessageType
import ir.omidtaheri.domain.datastate.StateMessage
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val movieRemoteDataSource: MovieRemoteDataSourceInterface,
    val multiMovieEntityDomainDataMapper: MultiMovieEntityDomainDataMapper,
    val genreEntityDomainDataMapper: GenreEntityDomainDataMapper
) : MovieGateWay {

    override fun getTopRatedMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {

        return movieRemoteDataSource.getTopRatedMovies(page)
            .map<DataState<MultiMovieDomainEntity>> {

                val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

                DataState.SUCCESS(
                    multiMovieDomain,
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }
            .onErrorReturn {
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            }
    }

    override fun getPopularMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.getPopularMovies(page)
            .map<DataState<MultiMovieDomainEntity>> {

                val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

                DataState.SUCCESS(
                    multiMovieDomain,
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

    override fun getGenreList(): Single<DataState<List<GenreDomainEntity>>> {
        return movieRemoteDataSource.getGenreList().map<DataState<List<GenreDomainEntity>>> {

            val genreListDomainEntity = it.map {
                genreEntityDomainDataMapper.mapFromDataEntity(it)
            }

            DataState.SUCCESS(
                genreListDomainEntity,
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }
            .onErrorReturn {
                DataState.ERROR(
                    StateMessage(
                        MessageHolder.MESSAGE(it.message  ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            }
    }

    override fun getUpComingMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.getUpComingMovies(page)
            .map<DataState<MultiMovieDomainEntity>> {

                val multiMovieDomain = multiMovieEntityDomainDataMapper.mapFromDataEntity(it)

                DataState.SUCCESS(
                    multiMovieDomain,
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
