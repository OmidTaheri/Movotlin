package ir.omidtaheri.mainpage.viewmodelTest

import android.app.Application
import android.content.Context
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.MessageType
import ir.omidtaheri.domain.datastate.StateMessage
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.interactor.FavorieMovie
import ir.omidtaheri.domain.interactor.GetMovieDetail
import ir.omidtaheri.domain.interactor.GetMovieImagesById
import ir.omidtaheri.domain.interactor.GetMovieVideosById
import ir.omidtaheri.domain.interactor.GetSimilarMoviesSinglePage
import ir.omidtaheri.domain.interactor.UnfavoriteMovie
import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import ir.omidtaheri.mainpage.mapper.GenreEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieDetailEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieImageEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.MovieVideoEntityUiDomainMapper
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {
    lateinit var detailViewModel: DetailViewModel

    @MockK
    lateinit var getDetailMovieUseCase: GetMovieDetail

    @MockK
    lateinit var getMovieImagesById: GetMovieImagesById

    @MockK
    lateinit var getMovieVideosById: GetMovieVideosById

    @MockK
    lateinit var getSimilarMovies: GetSimilarMoviesSinglePage

    @MockK
    lateinit var favorieMovie: FavorieMovie

    @MockK
    lateinit var unfavoriteMovie: UnfavoriteMovie

    val movieDetailEntityUiDomainMapper =
        MovieDetailEntityUiDomainMapper(GenreEntityUiDomainMapper())
    val movieImageEntityUiDomainMapper = MovieImageEntityUiDomainMapper()
    val movieVideoEntityUiDomainMapper = MovieVideoEntityUiDomainMapper()
    val movieEntityUiDomainMapper = MovieEntityUiDomainMapper()

    lateinit var application: Application

    @MockK(relaxed = true)
    lateinit var SimilarMoviesLiveData_MockObserver: Observer<PagingData<MovieUiEntity>>

    @MockK(relaxed = true)
    lateinit var DetailMovieLiveData_MockObserver: Observer<MovieDetailUiEntity>

    @MockK(relaxed = true)
    lateinit var LoadingLiveData_MockObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var ErrorSnackBar_LiveData_MockObserver: Observer<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        application =
            ApplicationProvider.getApplicationContext<Context>().applicationContext as Application

        detailViewModel = DetailViewModel(
            getDetailMovieUseCase,
            getMovieImagesById,
            getMovieVideosById,
            getSimilarMovies,
            favorieMovie,
            unfavoriteMovie,
            movieDetailEntityUiDomainMapper,
            movieImageEntityUiDomainMapper,
            movieVideoEntityUiDomainMapper,
            movieEntityUiDomainMapper,
            application
        )
    }

//    @Test
//    fun getSimilarMovies_successful() {
//
//        //Arrange
//        val FackPagingData = CreateFackPagingData_Successfull(5)
//
//        every { getSimilarMovies.execute(any()) } returns FackPagingData
//        detailViewModel.SimilarMoviesLiveData.observeForever(SimilarMoviesLiveData_MockObserver)
//
//        //Action
//        detailViewModel.getSimilarMovies(550)
//
//        //Asseration
//        val inputlist: ArrayList<MovieUiEntity> = arrayListOf()
//        val pagingDatainput = FackPagingData.blockingFirst().filter {
//            println("tag2 " + it.id)
//            inputlist.add(movieEntityUiDomainMapper.mapToUiEntity(it))
//            true
//        }
//
//
//        val capture_slot = slot<PagingData<MovieUiEntity>>()
//
//        verify { SimilarMoviesLiveData_MockObserver.onChanged(capture(capture_slot)) }
//
//
//        val capturedlist: ArrayList<MovieUiEntity> = arrayListOf()
//        capture_slot.captured.filter {
//            println("tag1 " + it.id)
//            capturedlist.add(it)
//            true
//        }
//
// //        assertEquals(
// //            capturedlist.get(0).overview,
// //            inputlist.get(0).overview
// //        )
//    }

//    @Test
//    fun getSimilarMovies_Failed() {
//
//        //Arrange
//        val FackPagingData = CreateFackPagingData_Failed(5)
//
//        every { getSimilarMovies.execute(any()) } returns FackPagingData
//        detailViewModel.SimilarMoviesLiveData.observeForever(SimilarMoviesLiveData_MockObserver)
//
//        //Action
//        detailViewModel.getSimilarMovies(550)
//
//        //Asseration
//        val testpagingObserver = FackPagingData.test()
//        testpagingObserver.await(2, TimeUnit.SECONDS)
//
//
//
//    }

    @Test
    fun getMovieDetail_Successfull() {
        // Arrange
        val movieDetailDomainEntity = MovieDetailDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genres = listOf(
                GenreDomainEntity(
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

        val SuccessFull_DataState = DataState.SUCCESS(
            movieDetailDomainEntity,
            StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
        )

        detailViewModel.isLoading.observeForever(LoadingLiveData_MockObserver)
        detailViewModel.detailLiveData.observeForever(DetailMovieLiveData_MockObserver)

        every { getDetailMovieUseCase.execute(any()) } returns
                Single.just(SuccessFull_DataState)

        // Action
        detailViewModel.getMovieDetail(550)

        // Asseration
        val capture_slot_loading = slot<Boolean>()
        val capture_slot_detailMovie = slot<MovieDetailUiEntity>()
        verify { LoadingLiveData_MockObserver.onChanged(capture(capture_slot_loading)) }
        verify { DetailMovieLiveData_MockObserver.onChanged(capture(capture_slot_detailMovie)) }

        assertEquals(capture_slot_loading.captured, false)
        assertEquals(
            capture_slot_detailMovie.captured.id,
            movieDetailEntityUiDomainMapper.mapToUiEntity(movieDetailDomainEntity).id
        )
    }

    @Test
    fun getMovieDetail_Failed() {
        // Arrange
        val Error_DataState = DataState.ERROR<MovieDetailDomainEntity>(
            StateMessage(
                MessageHolder.MESSAGE("Loading Error"),
                UiComponentType.SNACKBAR,
                MessageType.ERROR
            )
        )

        detailViewModel.ErrorSnackBar.observeForever(ErrorSnackBar_LiveData_MockObserver)

        every { getDetailMovieUseCase.execute(any()) } returns
                Single.just(Error_DataState)

        // Action
        detailViewModel.getMovieDetail(550)

        // Asseration

        val capture_slot_error = slot<String>()
        verify { ErrorSnackBar_LiveData_MockObserver.onChanged(capture(capture_slot_error)) }

        assertEquals(capture_slot_error.captured, "Loading Error")
    }
}
