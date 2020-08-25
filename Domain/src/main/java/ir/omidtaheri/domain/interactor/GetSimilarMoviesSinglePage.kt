package ir.omidtaheri.domain.interactor


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.interactor.base.MissingUseCaseParamsException
import ir.omidtaheri.domain.interactor.base.ObservablePagingDataUseCase
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.paigingSource.GetMovieListByGenreSource
import ir.omidtaheri.domain.paigingSource.GetSimilarMoviesSinglePageSource
import ir.omidtaheri.domain.paigingSource.GetSimilarMoviesSource
import javax.inject.Inject

class GetSimilarMoviesSinglePage @Inject constructor(
    schedulers: Schedulers,
    val discoverMovieRepository: DiscoverMovieGateWay
) : ObservablePagingDataUseCase<Int, PagingData<MovieDomainEntity>>(schedulers) {

    override fun buildSingle(MovieId: Int): Observable<PagingData<MovieDomainEntity>> {


        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GetSimilarMoviesSinglePageSource(MovieId, discoverMovieRepository) }
        ).observable

    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}