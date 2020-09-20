package ir.omidtaheri.mainpage.fackFactory.pagingUtils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.RxPagingSource
import androidx.paging.rxjava2.observable
import io.reactivex.Single
import ir.omidtaheri.domain.entity.MovieDomainEntity

fun CreateFackPagingData_Successfull(page_size: Int) = Pager(
    config = PagingConfig(page_size),
    pagingSourceFactory = { CreateFackPagingSource_Successfull() }
).observable

fun CreateFackPagingData_Failed(page_size: Int) = Pager(
    config = PagingConfig(page_size),
    pagingSourceFactory = { CreateFackPagingSource_Failed() }
).observable

fun CreateFackPagingSource_Successfull() =
    FackPagingSource_Successfull()

fun CreateFackPagingSource_Failed() =
    FackPagingSource_Failed()

class FackPagingSource_Failed : RxPagingSource<Int, MovieDomainEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieDomainEntity>> {

        return Single.just(
            LoadResult.Error(
                Throwable("Paging Error")
            )
        )
    }
}

class FackPagingSource_Successfull : RxPagingSource<Int, MovieDomainEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieDomainEntity>> {

        return Single.just(
            LoadResult.Page(
                CreateMovieDomianEntityFackList(), null,
                2
            )
        )
    }
}

private fun CreateMovieDomianEntityFackList(): List<MovieDomainEntity> {

    return listOf(

        MovieDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genreIds = listOf(
                18
            ),
            id = 550,
            overview = "1- A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            releaseDate = "1999-10-12",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        ),

        MovieDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genreIds = listOf(
                18
            ),
            id = 551,
            overview = "2- A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            releaseDate = "1999-10-12",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        ),

        MovieDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genreIds = listOf(
                18
            ),
            id = 552,
            overview = "3- A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            releaseDate = "1999-10-12",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        ),

        MovieDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genreIds = listOf(
                18
            ),
            id = 553,
            overview = "4- A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            releaseDate = "1999-10-12",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        ),

        MovieDomainEntity(
            backdropPath = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            genreIds = listOf(
                18
            ),
            id = 554,
            overview = "5- A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            posterPath = null,
            releaseDate = "1999-10-12",
            title = "Fight Club",
            video = false,
            voteAverage = 7.8
        )
    )
}
