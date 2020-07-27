package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.remote.entity.response.GenreResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.MovieDetailResponseRemoteEntity
import javax.inject.Inject

class GenreResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<GenreResponseRemoteEntity, List<GenreDataEntity>> {
    override fun mapFromDTO(from: GenreResponseRemoteEntity): List<GenreDataEntity> {
        return from.genres.map {
            GenreDataEntity(it.id, it.name)
        }
    }
}