package ir.omidtaheri.local.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.local.entity.MovieLocalEntity

class MovieEntityDataLocalMapper : Data_Local_EntityMapper<MovieDataEntity, MovieLocalEntity> {

    override fun mapFromDataEntity(from: MovieDataEntity): MovieLocalEntity {
        return MovieLocalEntity(from.id, from.title, from.rating, from.posterPath)
    }

    override fun mapToDataEntity(from: MovieLocalEntity): MovieDataEntity {
        return MovieDataEntity(from.id, from.title, from.rating, from.picture, false)
    }


}