package ir.omidtaheri.domain.paigingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.gateway.DiscoverMovieGateWay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class GetMovieListByGenreSource(
    private val genreId: Int,
    private val discoverMovieRepository: DiscoverMovieGateWay
) :
    PagingSource<Int, MovieDomainEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomainEntity> {
        val pageNumber: Int = params.key ?: 1

        val params: Map<String, Int> = mapOf("with_genres" to genreId, "page" to pageNumber)

        return discoverMovieRepository.getMovieListByGenreId(params).map {

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
        }.single()
    }


    override fun getRefreshKey(state: PagingState<Int, MovieDomainEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}
