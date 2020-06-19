package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.remote.entity.response.MoviesResponseRemoteEntity

class MovieResponseToDataEntityMapper : ResponseToData_EntityMapper<MoviesResponseRemoteEntity, List<MovieDataEntity>> {

    override fun mapFromDTO(from: MoviesResponseRemoteEntity): List<MovieDataEntity> {
        TODO("Not yet implemented")
    }


}