package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.remote.entity.response.MovieVideosResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.Result
import javax.inject.Inject

class MovieVideosResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<MovieVideosResponseRemoteEntity, MovieVideoDataEntity> {
    override fun mapFromDTO(from: MovieVideosResponseRemoteEntity): MovieVideoDataEntity {

        return MovieVideoDataEntity(from.id, mapFromVideoResultDTO(from.results))
    }

    fun mapFromVideoResultDTO(videoResultList: List<Result>): List<ir.omidtaheri.data.entity.Result> {
        return videoResultList.map {
            ir.omidtaheri.data.entity.Result(it.id, it.key, it.name, it.site, it.size, it.type)
        }
    }
}
