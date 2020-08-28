package ir.omidtaheri.data.repository

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieImageEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieVideoEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.MessageType
import ir.omidtaheri.domain.datastate.StateMessage
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams
import javax.inject.Inject

class DiscoverMovieRepository @Inject constructor(
    val movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface,
    val movieDetailEntityMapper: MovieDetailEntityDomainDataMapper,
    val movieImageEntityDomainDataMapper: MovieImageEntityDomainDataMapper,
    val movieVideoEntityDomainDataMapper: MovieVideoEntityDomainDataMapper,
    val multiMovieEntityDomainDataMapper: MultiMovieEntityDomainDataMapper
) : DiscoverMovieGateWay {

    override fun getMovieDetailById(movieId: Int): Single<DataState<MovieDetailDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieDetailById(movieId)
            .map<DataState<MovieDetailDomainEntity>> {
                DataState.SUCCESS(
                    movieDetailEntityMapper.mapFromDataEntity(it),
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

    override fun getMovieListByGenreId(params: Map<String, Any>): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieListByGenreId(params)
            .map<DataState<MultiMovieDomainEntity>> {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun getMovieImagesById(movieId: Int): Single<DataState<MovieImageDomainEntity>> {

        return movieDetailRemoteDataSource.getMovieImagesById(movieId)
            .map<DataState<MovieImageDomainEntity>> {
                DataState.SUCCESS(
                    movieImageEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun getMovieVideosById(movieId: Int): Single<DataState<MovieVideoDomainEntity>> {
        return movieDetailRemoteDataSource.getMovieVideosById(movieId)
            .map<DataState<MovieVideoDomainEntity>> {
                DataState.SUCCESS(
                    movieVideoEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun getSimilarMovieById(params: GetSimilarMoviesParams): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.getSimilarMovieById(params.movieId, params.page)
            .map<DataState<MultiMovieDomainEntity>> {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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

    override fun searchMovieByQuery(params: SearchMovieByQueryParams): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.searchMovieByQuery(params.query, params.page)
            .map<DataState<MultiMovieDomainEntity>> {
                DataState.SUCCESS(
                    multiMovieEntityDomainDataMapper.mapFromDataEntity(it),
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
