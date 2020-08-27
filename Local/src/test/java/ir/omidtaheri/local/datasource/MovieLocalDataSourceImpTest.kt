package ir.omidtaheri.local.datasource

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.local.BaseTest
import ir.omidtaheri.local.fackEntity.FackFacktory
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieLocalDataSourceImpTest : BaseTest() {

    lateinit var movieLocalDataSourceImp: MovieLocalDataSourceImp

    @Before
    override fun setUp() {
        super.setUp()
        movieLocalDataSourceImp = MovieLocalDataSourceImp(
            db.getMovieDao(),
            MovieEntityDataLocalMapper()
        )
    }

    @Test
    fun favoriteMovie() {

        val testObserver =
            movieLocalDataSourceImp.favoriteMovie(FackFacktory.favoritedMovieDataEntity).test()

        testObserver.awaitTerminalEvent()

        val firstItem: FavoritedMovieDataEntity =
            movieLocalDataSourceImp.getFavoritedMoviesList().blockingFirst()[0]

        assertEquals(firstItem, FackFacktory.favoritedMovieDataEntity)
    }
}
