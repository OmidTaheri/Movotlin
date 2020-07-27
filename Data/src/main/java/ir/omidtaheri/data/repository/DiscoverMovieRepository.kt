package ir.omidtaheri.data.repository

import io.reactivex.rxjava3.core.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity

import ir.omidtaheri.domain.gateway.MovieDetailGateWay
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    val movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface,
    val movieDetailEntityMapper: MovieDetailEntityDomainDataMapper
) : MovieDetailGateWay {

    override fun GetMovieDetail(MovieId: Long): Single<DataState<MovieDetailDomainEntity>> {
        return movieDetailRemoteDataSource.GetMovieDetail(MovieId)
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


}