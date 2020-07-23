package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity

class MovieDetailEntityUiDomainMapper :
    Ui_Domain_Mapper<MovieDetailUiEntity, MovieDetailDomainEntity> {
    override fun mapFromUiEntity(from: MovieDetailUiEntity): MovieDetailDomainEntity {
        return MovieDetailDomainEntity(
            from.id,
            from.title,
            from.voteAverage,
            from.posterPath,
            from.backdropPath,
            from.overview,
            from.tagline,
            from.isFavorite
        )
    }

    override fun mapToUiEntity(from: MovieDetailDomainEntity): MovieDetailUiEntity {

        return MovieDetailUiEntity(
            from.id,
            from.title,
            from.voteAverage,
            from.posterPath,
            from.backdropPath,
            from.overview,
            from.tagline,
            from.isFavorite
        )
    }
}