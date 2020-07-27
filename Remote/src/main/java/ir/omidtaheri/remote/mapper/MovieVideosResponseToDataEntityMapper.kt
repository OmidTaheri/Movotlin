package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.remote.entity.response.*
import javax.inject.Inject

class MovieVideosResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<MovieVideosResponseRemoteEntity, MovieVideoDataEntity> {
    override fun mapFromDTO(from: MovieVideosResponseRemoteEntity): MovieVideoDataEntity {

        return MovieVideoDataEntity(from.id, mapFromVideoResultDTO(from.results))
    }

    fun mapFromVideoResultDTO(video_result_list: List<Result>): List<ir.omidtaheri.data.entity.Result> {
        return video_result_list.map {
            ir.omidtaheri.data.entity.Result(it.id, it.key, it.name, it.site, it.size, it.type)
        }

    }

}