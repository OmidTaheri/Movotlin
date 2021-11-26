package ir.omidtaheri.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowPagingDataUseCase
import ir.omidtaheri.domain.paigingSource.GetSimilarMoviesSinglePageSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMoviesSinglePage @Inject constructor(
    private val discoverMovieRepository: DiscoverMovieGateWay
) : FlowPagingDataUseCase<Int, PagingData<MovieDomainEntity>>() {

    override fun buildSingle(movieId: Int): Flow<PagingData<MovieDomainEntity>> {

        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                GetSimilarMoviesSinglePageSource(
                    movieId,
                    discoverMovieRepository
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
