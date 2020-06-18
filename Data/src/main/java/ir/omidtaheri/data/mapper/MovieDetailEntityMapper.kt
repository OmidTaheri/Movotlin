package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.domain.entity.MovieDetailEntity

class MovieDetailEntityMapper : Mapper<MovieDetailDataEntity, MovieDetailEntity> {
    override fun mapFromDataEntity(from: MovieDetailDataEntity): MovieDetailEntity {

        return MovieDetailEntity(from.id,from.title,from.voteAverage,from.posterPath,from.backdropPath,from.overview,from.tagline,from.isFavorite)
    }

    override fun mapToDataEntity(from: MovieDetailEntity): MovieDetailDataEntity {

        return MovieDetailDataEntity(from.id,from.title,from.voteAverage,from.posterPath,from.backdropPath,from.overview,from.tagline,from.isFavorite)

    }
}