package ir.omidtaheri.local.datasource

import org.junit.After
import org.junit.Before
import org.junit.Test

import ir.omidtaheri.local.BaseTest
import ir.omidtaheri.local.fackEntity.FackFacktory
import ir.omidtaheri.local.mapper.MovieEntityDataLocalMapper

import org.junit.Assert.*

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
            movieLocalDataSourceImp.FavoriteMovie(FackFacktory.favoritedMovieDataEntity).test()

        testObserver.assertComplete()


        assertEquals(db.getMovieDao().GetFavoritedMoviesList().test().valueCount(), 1)

        assertTrue(
            db.getMovieDao().GetFavoritedMoviesList().test()
                .values()[0][0].equals(FackFacktory.favoritedMovieDataEntity)
        )


    }
}