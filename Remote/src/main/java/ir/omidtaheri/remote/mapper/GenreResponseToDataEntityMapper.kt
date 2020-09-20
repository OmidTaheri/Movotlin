package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.remote.entity.response.GenreResponseRemoteEntity
import javax.inject.Inject

class GenreResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<GenreResponseRemoteEntity, List<GenreDataEntity>> {
    override fun mapFromDTO(from: GenreResponseRemoteEntity): List<GenreDataEntity> {
        return from.genres.map {
            GenreDataEntity(it.id, it.name)
        }
    }
}
