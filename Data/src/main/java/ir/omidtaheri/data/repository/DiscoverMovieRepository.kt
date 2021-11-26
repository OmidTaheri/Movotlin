package ir.omidtaheri.data.repository

import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieImageEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieVideoEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoverMovieRepository @Inject constructor(
    private val movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface,
    private val movieDetailEntityMapper: MovieDetailEntityDomainDataMapper,
    private val movieImageEntityDomainDataMapper: MovieImageEntityDomainDataMapper,
    private val movieVideoEntityDomainDataMapper: MovieVideoEntityDomainDataMapper,
    private val multiMovieEntityDomainDataMapper: MultiMovieEntityDomainDataMapper
) : DiscoverMovieGateWay {

    override fun getMovieDetailById(movieId: Int): Flow<DataState<MovieDetailDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieDetailById(movieId)
            .map {
                DataState.SUCCESS(
                    movieDetailEntityMapper.mapFromDataEntity(it),
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }.catch<DataState<MovieDetailDomainEntity>> {
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

    override fun getMovieListByGenreId(params: Map<String, Int>): Flow<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieListByGenreId(params)
            .map {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun getMovieImagesById(movieId: Int): Flow<DataState<MovieImageDomainEntity>> {

        return movieDetailRemoteDataSource.getMovieImagesById(movieId)
            .map {
                DataState.SUCCESS(
                    movieImageEntityDomainDataMapper.mapFromDataEntity(it),
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }.catch<DataState<MovieImageDomainEntity>> {
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

    override fun getMovieVideosById(movieId: Int): Flow<DataState<MovieVideoDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieVideosById(movieId)
            .map {
                DataState.SUCCESS(
                    movieVideoEntityDomainDataMapper.mapFromDataEntity(it),
                    StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
                )
            }.catch<DataState<MovieVideoDomainEntity>> {
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

    override fun getSimilarMovieById(params: GetSimilarMoviesParams): Flow<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.getSimilarMovieById(params.movieId, params.page)
            .map {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun searchMovieByQuery(params: SearchMovieByQueryParams): Flow<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.searchMovieByQuery(params.query, params.page)
            .map {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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
