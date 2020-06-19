package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity

class MovieDetailEntityDomainDataMapper : Domain_Data_Mapper<MovieDetailDataEntity, MovieDetailDomainEntity> {
    override fun mapFromDataEntity(from: MovieDetailDataEntity): MovieDetailDomainEntity {

        return MovieDetailDomainEntity(from.id,from.title,from.voteAverage,from.posterPath,from.backdropPath,from.overview,from.tagline,from.isFavorite)
    }

    override fun mapToDataEntity(from: MovieDetailDomainEntity): MovieDetailDataEntity {

        return MovieDetailDataEntity(from.id,from.title,from.voteAverage,from.posterPath,from.backdropPath,from.overview,from.tagline,from.isFavorite)

    }
}