package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.remote.entity.response.MovieDetailResponseRemoteEntity
import javax.inject.Inject

class MovieDetailResponseToDataEntityMapper @Inject constructor() :ResponseToData_EntityMapper<MovieDetailResponseRemoteEntity, MovieDetailDataEntity> {
    override fun mapFromDTO(from: MovieDetailResponseRemoteEntity): MovieDetailDataEntity {
        TODO("Not yet implemented")
    }
}