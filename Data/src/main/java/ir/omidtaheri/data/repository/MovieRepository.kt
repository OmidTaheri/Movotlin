package ir.omidtaheri.data.repositoryTest



import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.GenreEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val movieRemoteDataSource: MovieRemoteDataSourceInterface,
    val multiMovieEntityDomainDataMapper: MultiMovieEntityDomainDataMapper,
    val genreEntityDomainDataMapper: GenreEntityDomainDataMapper
) : MovieGateWay {


    override fun GetTopRatedMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {

        return movieRemoteDataSource.GetTopRatedMovies(page)
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

    override fun GetPopularMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.GetPopularMovies(page)
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


    override fun GetGenreList(): Single<DataState<List<GenreDomainEntity>>> {
        return movieRemoteDataSource.GetGenreList().map<DataState<List<GenreDomainEntity>>> {


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
                        MessageHolder.MESSAGE(it.message ?: "Error"),
                        UiComponentType.SNACKBAR,
                        MessageType.ERROR
                    )
                )
            }
    }

    override fun GetUpComingMovies(page: Int): Single<DataState<MultiMovieDomainEntity>> {
        return movieRemoteDataSource.GetUpComingMovies(page)
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