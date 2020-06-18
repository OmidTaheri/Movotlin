package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.domain.entity.MovieEntity

class MovieEntityMapper : Mapper<MovieDataEntity, MovieEntity> {
    override fun mapFromDataEntity(from: MovieDataEntity): MovieEntity {

        return MovieEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }

    override fun mapToDataEntity(from: MovieEntity): MovieDataEntity {

        return MovieDataEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }
}