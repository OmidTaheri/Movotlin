package ir.omidtaheri.domain.paigingSource

import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import ir.omidtaheri.domain.gateway.MovieGateWay
import ir.omidtaheri.domain.interactor.usecaseParam.GetSimilarMoviesParams
import ir.omidtaheri.domain.interactor.usecaseParam.SearchMovieByQueryParams

class GetSimilarMoviesSinglePageSource(
    val MovieId: Int,
    val discoverMovieRepository: DiscoverMovieGateWay
) :
    RxPagingSource<Int, MovieDomainEntity>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieDomainEntity>> {

        val PageNumber: Int = params.key ?: 1

        val params = GetSimilarMoviesParams(MovieId, PageNumber)
        return discoverMovieRepository.GetSimilarMovieById(params)
            .map {

                when (it) {

                    is DataState.SUCCESS -> {
                        LoadResult.Page(
                            it.data!!.results
                            , null,
                            if (it.data.page == 1) null else 1
                        )


                    }

                    is DataState.ERROR -> {
                        it.stateMessage?.message.let {
                            when (it) {
                                is MessageHolder.MESSAGE ->
                                    LoadResult.Error<Int, MovieDomainEntity>(
                                        Throwable(it.Message)
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