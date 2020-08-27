package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import javax.inject.Inject

class FavoritedMovieEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<FavoritedMovieDataEntity, FavoritedMovieDomainEntity> {
    override fun mapFromDataEntity(from: FavoritedMovieDataEntity): FavoritedMovieDomainEntity {
        return FavoritedMovieDomainEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }

    override fun mapToDataEntity(from: FavoritedMovieDomainEntity): FavoritedMovieDataEntity {
        return FavoritedMovieDataEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }
}
