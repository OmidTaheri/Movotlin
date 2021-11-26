package ir.omidtaheri.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.FlowPagingDataUseCase
import ir.omidtaheri.domain.paigingSource.GetUpcomingMoviesSinglePageSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesSinglePage @Inject constructor(
    private val movieRepository: MovieGateWay
) : FlowPagingDataUseCase<Unit, PagingData<MovieDomainEntity>>() {

    override fun buildSingle(params: Unit): Flow<PagingData<MovieDomainEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GetUpcomingMoviesSinglePageSource(movieRepository) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
