package ir.omidtaheri.domain.gateway

import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import kotlinx.coroutines.flow.Flow

interface MovieGateWay {
    fun getTopRatedMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>>
    fun getPopularMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>>
    fun getGenreList(): Flow<DataState<List<GenreDomainEntity>>>
    fun getUpComingMovies(page: Int): Flow<DataState<MultiMovieDomainEntity>>
}
