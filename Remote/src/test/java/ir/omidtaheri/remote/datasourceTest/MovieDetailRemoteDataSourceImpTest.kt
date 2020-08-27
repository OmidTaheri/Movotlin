package ir.omidtaheri.remote.datasourceTest

import ir.omidtaheri.remote.BaseTest
import ir.omidtaheri.remote.datasource.MovieDetailRemoteDataSourceImp
import ir.omidtaheri.remote.mapper.MovieDetailResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieImagesResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieResponseToDataEntityMapper
import ir.omidtaheri.remote.mapper.MovieVideosResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MovieDetailApi
import ir.omidtaheri.remote.testUtils.getJson
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class MovieDetailRemoteDataSourceImpTest : BaseTest() {

    lateinit var movieDetailApi: MovieDetailApi

    lateinit var movieDetailRemoteDataSourceImp: MovieDetailRemoteDataSourceImp

    val movieDetailResponseToDataEntityMapper = MovieDetailResponseToDataEntityMapper()

    val movieImagesResponseToDataEntityMapper = MovieImagesResponseToDataEntityMapper()

    val movieVideosResponseToDataEntityMapper = MovieVideosResponseToDataEntityMapper()

    val movieResponseToDataEntityMapper = MovieResponseToDataEntityMapper()

    @Before
    fun setUp() {
        super.setup()
        movieDetailApi = retrofit.create(MovieDetailApi::class.java)

        movieDetailRemoteDataSourceImp = MovieDetailRemoteDataSourceImp(
            movieDetailApi,
            movieDetailResponseToDataEntityMapper,
            movieImagesResponseToDataEntityMapper,
            movieVideosResponseToDataEntityMapper,
            movieResponseToDataEntityMapper
        )
    }

    @Test
    fun getMovieDetailById_status_200() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("mock-responses/getMovieDetailById-status-200.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getMovieDetailById(550).test()

        testObserver.assertComplete()

        val firstItem = testObserver
            .values()
            .first()

        assertEquals(
            firstItem.overview,
            "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion."
        )
    }

    @Test
    fun getMovieDetailById_status_401() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(getJson("mock-responses/getMovieDetailById-status-401.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getMovieDetailById(550).test()

        assertEquals(testObserver.errorCount(), 1)

        testObserver.assertError(HttpException::class.java)

        testObserver.assertError {

            when (it) {

                is HttpException -> {
                    println(it.response()?.errorBody()?.string())
                    assertEquals(it.code(), 401)
                    true
                }
                else -> false
            }
        }
    }

    @Test
    fun getMovieDetailById_status_404() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(getJson("mock-responses/getMovieDetailById-status-404.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getMovieDetailById(550).test()

        assertEquals(testObserver.errorCount(), 1)
        testObserver.assertError(HttpException::class.java)

        testObserver.assertError {

            when (it) {

                is HttpException -> {
                    println(it.response()?.errorBody()?.string())
                    assertEquals(it.code(), 404)
                    true
                }
                else -> false
            }
        }
    }

    @Test
    fun getSimilarMovieById_status_200() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("mock-responses/getSimilarMovieById-status-200.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getSimilarMovieById(550, 1).test()

        testObserver.assertComplete()

        val firstItem = testObserver
            .values()
            .first()

        assertEquals(
            firstItem.results[0].id,
            106912
        )
    }

    @Test
    fun getSimilarMovieById_status_401() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(getJson("mock-responses/getSimilarMovieById-status-401.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getSimilarMovieById(550, 1).test()

        assertEquals(testObserver.errorCount(), 1)

        testObserver.assertError(HttpException::class.java)

        testObserver.assertError {

            when (it) {

                is HttpException -> {
                    println(it.response()?.errorBody()?.string())
                    assertEquals(it.code(), 401)
                    true
                }
                else -> false
            }
        }
    }

    @Test
    fun getSimilarMovieById_status_404() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(getJson("mock-responses/getSimilarMovieById-status-404.json"))
        )

        val testObserver = movieDetailRemoteDataSourceImp.getSimilarMovieById(550, 1).test()

        assertEquals(testObserver.errorCount(), 1)
        testObserver.assertError(HttpException::class.java)

        testObserver.assertError {

            when (it) {

                is HttpException -> {
                    println(it.response()?.errorBody()?.string())
                    assertEquals(it.code(), 404)
                    true
                }
                else -> false
            }
        }
    }
}
