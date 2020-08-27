package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.mainpage.entity.FavoritedMovieUiEntity
import javax.inject.Inject

class FavoritedMovieEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<FavoritedMovieUiEntity, FavoritedMovieDomainEntity> {
    override fun mapFromUiEntity(from: FavoritedMovieUiEntity): FavoritedMovieDomainEntity {
        return FavoritedMovieDomainEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }

    override fun mapToUiEntity(from: FavoritedMovieDomainEntity): FavoritedMovieUiEntity {
        return FavoritedMovieUiEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }
}
