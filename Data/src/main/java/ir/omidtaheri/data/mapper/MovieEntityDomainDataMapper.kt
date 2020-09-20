package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import javax.inject.Inject

class MovieEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<MovieDataEntity, MovieDomainEntity> {
    override fun mapFromDataEntity(from: MovieDataEntity): MovieDomainEntity {

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

    override fun mapToDataEntity(from: MovieDomainEntity): MovieDataEntity {

        return MovieDataEntity(
            false,
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
