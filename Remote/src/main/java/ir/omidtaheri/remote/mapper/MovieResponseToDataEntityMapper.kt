package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MultiMovieResult
import javax.inject.Inject

class MovieResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<MultiMovieResponseRemoteEntity, MultiMovieDataEntity> {


    override fun mapFromDTO(from: MultiMovieResponseRemoteEntity): MultiMovieDataEntity {

        return MultiMovieDataEntity(
            from.page,
            mapFromMultiMovieResultDTO(from.results),
            from.total_pages,
            from.total_results
        )
    }


    fun mapFromMultiMovieResultDTO(dto_result_list: List<MultiMovieResult>): List<MovieDataEntity> {

        return dto_result_list.map {
            MovieDataEntity(
                it.adult,
                it.backdrop_path,
                it.genre_ids,
                it.id,
                it.overview,
                it.poster_path,
                it.release_date,
                it.title,
                it.video,
                it.vote_average
            )
        }
    }

}