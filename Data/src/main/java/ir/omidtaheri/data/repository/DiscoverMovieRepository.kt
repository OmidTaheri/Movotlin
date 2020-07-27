package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieImageEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieVideoEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.*

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


    override fun GetMovieDetailById(MovieId: Int): Single<DataState<MovieDetailDomainEntity>> {
        return movieDetailRemoteDataSource.GetMovieDetailById(MovieId)
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

    override fun GetMovieListByGenreId(Params: Map<String, Any>): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.GetMovieListByGenreId(Params)
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


    override fun GetMovieImagesById(MovieId: Int): Single<DataState<MovieImageDomainEntity>> {

        return movieDetailRemoteDataSource.GetMovieImagesById(MovieId)
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

    override fun GetMovieVideosById(MovieId: Int): Single<DataState<MovieVideoDomainEntity>> {
        return movieDetailRemoteDataSource.GetMovieVideosById(MovieId)
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

    override fun GetSimilarMovieById(Params: GetSimilarMoviesParams): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.GetSimilarMovieById(Params.MovieId, Params.page)
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

    override fun SearchMovieByQuery(params: SearchMovieByQueryParams): Single<DataState<MultiMovieDomainEntity>> {
        return movieDetailRemoteDataSource.SearchMovieByQuery(params.query, params.page)
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