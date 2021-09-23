package ir.omidtaheri.detailpage.mapper

import ir.omidtaheri.detailpage.entity.MovieDetailUiEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import javax.inject.Inject

class MovieDetailEntityUiDomainMapper @Inject constructor(val genreEntityUiDomainMapper: GenreEntityUiDomainMapper) :
    UiDomainMapper<MovieDetailUiEntity, MovieDetailDomainEntity> {
    override fun mapFromUiEntity(from: MovieDetailUiEntity): MovieDetailDomainEntity {
        return MovieDetailDomainEntity(
            from.backdropPath,
            from.genres.map {
                genreEntityUiDomainMapper.mapFromUiEntity(it)
            },
            from.id,
            from.overview,
            from.posterPath,
            from.tagline,
            from.title,
            from.video,
            from.voteAverage
        )
    }

    override fun mapToUiEntity(from: MovieDetailDomainEntity): MovieDetailUiEntity {

        return MovieDetailUiEntity(
            from.backdropPath,
            from.genres.map {
                genreEntityUiDomainMapper.mapToUiEntity(it)
            },
            from.id,
            from.overview,
            from.posterPath,
            from.tagline,
            from.title,
            from.video,
            from.voteAverage
        )
    }
}
