package ir.omidtaheri.data.repositoryTest

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.MovieDetailRemoteDataSourceInterface
import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.mapper.*
import ir.omidtaheri.data.repository.DiscoverMovieRepository
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

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
        //Arange
        val inputEntity = MovieDetailDataEntity(
            backdrop_path = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genres = listOf(
                GenreDataEntity(
                    id = 18,
                    name = "Drama"
                )
            ),
            id = 550,
            overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            poster_path = null,
            tagline = "How much can you know about yourself if you've never been in a fight?",
            title = "Fight Club",
            video = false,
            vote_average = 7.8
        )
        val testObservable = Single.just(inputEntity)
        every { movieDetailRemoteDataSource.GetMovieDetailById(any()) } returns testObservable
        ///Action
        val testObserver = discoverMovieRepository.GetMovieDetailById(550).test()
        testObserver.awaitTerminalEvent()
        ///Assertion

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

        every { movieDetailRemoteDataSource.GetMovieDetailById(any()) } returns Single.error(
            RuntimeException("RunTimeError")
        )


        val testObserver = discoverMovieRepository.GetMovieDetailById(550).test()
        testObserver.awaitTerminalEvent()

        val Response_data = testObserver.values()[0]
        when (Response_data) {

            is DataState.ERROR -> {

                when (Response_data.stateMessage?.message) {
                    is MessageHolder.MESSAGE ->
                        assertEquals((Response_data.stateMessage?.message as MessageHolder.MESSAGE).Message, "RunTimeError")
                }


            }


        }


    }
}