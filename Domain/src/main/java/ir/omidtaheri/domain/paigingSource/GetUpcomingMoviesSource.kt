package ir.omidtaheri.domain.paigingSource

import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers

class GetUpcomingMoviesSource(val movieRepository: MovieGateWay, val schedulers: Schedulers) :
    RxPagingSource<Int, MovieDomainEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieDomainEntity>> {

        val pageNumber: Int = params.key ?: 1

        return movieRepository.getUpComingMovies(pageNumber)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
            .map {

                when (it) {

                    is DataState.SUCCESS -> {
                        LoadResult.Page(
                            it.data!!.results, null,
                            if (it.data.page != it.data.totalPages) (it.data!!.page) + 1 else null
                        )
                    }

                    is DataState.ERROR -> {
                        it.stateMessage?.message.let {
                            when (it) {
                                is MessageHolder.MESSAGE ->
                                    LoadResult.Error<Int, MovieDomainEntity>(
                                        Throwable(it.message)
                                    )

                                else -> LoadResult.Error<Int, MovieDomainEntity>(
                                    Throwable("Error")
                                )
                            }
                        }
                    }
                }
            }
    }
}
