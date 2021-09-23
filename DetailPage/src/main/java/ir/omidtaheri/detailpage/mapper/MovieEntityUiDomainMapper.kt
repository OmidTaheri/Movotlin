package ir.omidtaheri.detailpage.mapper

import ir.omidtaheri.detailpage.entity.MovieUiEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import javax.inject.Inject

class MovieEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<MovieUiEntity, MovieDomainEntity> {

    override fun mapFromUiEntity(from: MovieUiEntity): MovieDomainEntity {

        return MovieDomainEntity(
            from.backdropPath,
            from.genreIds,
            from.id,
            from.overview,
            from.posterPath,
            from.releaseDate,
            from.title,
            from.video,
            from.voteAverage
        )
    }

    override fun mapToUiEntity(from: MovieDomainEntity): MovieUiEntity {
        return MovieUiEntity(
            from.backdropPath,
            from.genreIds,
            from.id,
            from.overview,
            from.posterPath,
            from.releaseDate,
            from.title,
            from.video,
            from.voteAverage
        )
    }
}
