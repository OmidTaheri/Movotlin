package ir.omidtaheri.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.ObservablePagingDataUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.paigingSource.SearchMovieByQuerySource
import javax.inject.Inject

class SearchMoviesByQuery @Inject constructor(
    schedulers: Schedulers,
    val discoverMovieRepository: DiscoverMovieGateWay
) :
    ObservablePagingDataUseCase<String, PagingData<MovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: String): Observable<PagingData<MovieDomainEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { SearchMovieByQuerySource(params, discoverMovieRepository,schedulers) }
        ).observable
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
