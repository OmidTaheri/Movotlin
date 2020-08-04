package ir.omidtaheri.domain.interactor


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.ObservablePagingDataUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.paigingSource.GetTopRatedMoviesSource
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    schedulers: Schedulers,
    val movieRepository: MovieGateWay
) :
    ObservablePagingDataUseCase<Unit, PagingData<MovieDomainEntity>>(schedulers) {

    override fun buildSingle(params: Unit): Observable<PagingData<MovieDomainEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GetTopRatedMoviesSource(movieRepository) }
        ).observable

    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}