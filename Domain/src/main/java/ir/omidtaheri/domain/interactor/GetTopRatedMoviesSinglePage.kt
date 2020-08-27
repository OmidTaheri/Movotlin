package ir.omidtaheri.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.ObservablePagingDataUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.paigingSource.GetTopRatedMoviesSinglePageSource
import javax.inject.Inject

class GetTopRatedMoviesSinglePage @Inject constructor(
    schedulers: Schedulers,
    val movieRepository: MovieGateWay
) :
    ObservablePagingDataUseCase<Unit, PagingData<MovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: Unit): Observable<PagingData<MovieDomainEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GetTopRatedMoviesSinglePageSource(movieRepository) }
        ).observable
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
