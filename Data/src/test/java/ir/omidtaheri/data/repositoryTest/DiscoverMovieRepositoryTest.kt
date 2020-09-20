package ir.omidtaheri.data.repositoryTest

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.mapper.GenreEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieDetailEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieImageEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MovieVideoEntityDomainDataMapper
import ir.omidtaheri.data.mapper.MultiMovieEntityDomainDataMapper
import ir.omidtaheri.data.repository.DiscoverMovieRepository
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DiscoverMovieRepositoryTest {

    lateinit var discoverMovieRepository: DiscoverMovieRepository

    @MockK
    lateinit var movieDetailRemoteDataSource: MovieDetailRemoteDataSourceInterface

    val movieDetailEntityMapper = MovieDetailEntityDomainDataMapper(GenreEntityDomainDataMapper())
    val movieImageEntityDomainDataMapper = MovieImageEntityDomainDataMapper()
    val movieVideoEntityDomainDataMapper = MovieVideoEntityDomainDataMapper()
    val multiMovieEntityDomainDataMapper =
        MultiMovieEntityDomainDataMapper(MovieEntityDomainDataMapper())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        discoverMovieRepository = DiscoverMovieRepository(
            movieDetailRemoteDataSource,
            movieDetailEntityMapper,
            movieImageEntityDomainDataMapper,
            movieVideoEntityDomainDataMapper,
            multiMovieEntityDomainDataMapper
        )
    }

    @Test
    fun getMovieDetailById_Successfull() {
        // Arange
        val inputEntity = MovieDetailDataEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genres = listOf(
                GenreDataEntity(
                    id = 18,
                    name = "Drama"
                )
            ),
            id = 550,
            overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            tagline = "How much can you know about yourself if you've never been in a fight?",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        )
        val testObservable = Single.just(inputEntity)
        every { movieDetailRemoteDataSource.getMovieDetailById(any()) } returns testObservable
        // /Action
        val testObserver = discoverMovieRepository.getMovieDetailById(550).test()
        testObserver.awaitTerminalEvent()
        // /Assertion

        val Response_data = testObserver.values()[0]
        when (Response_data) {

            is DataState.SUCCESS -> {
                assertEquals(
                    Response_data.data,
                    movieDetailEntityMapper.mapFromDataEntity(inputEntity)
                )
            }
        }
    }

    @Test
    fun getMovieDetailById_Failed() {

        every { movieDetailRemoteDataSource.getMovieDetailById(any()) } returns Single.error(
            RuntimeException("RunTimeError")
        )

        val testObserver = discoverMovieRepository.getMovieDetailById(550).test()
        testObserver.awaitTerminalEvent()

        val Response_data = testObserver.values()[0]
        when (Response_data) {

            is DataState.ERROR -> {

                when (Response_data.stateMessage?.message) {
                    is MessageHolder.MESSAGE ->
                        assertEquals(
                            (Response_data.stateMessage?.message as MessageHolder.MESSAGE).message,
                            "RunTimeError"
                        )
                }
            }
        }
    }
}
