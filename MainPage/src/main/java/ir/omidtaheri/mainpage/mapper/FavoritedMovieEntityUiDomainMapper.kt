package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import ir.omidtaheri.mainpage.entity.FavoritedMovieUiEntity
import javax.inject.Inject

class FavoritedMovieEntityUiDomainMapper @Inject constructor() :
    Ui_Domain_Mapper<FavoritedMovieUiEntity, FavoritedMovieDomainEntity> {
    override fun mapFromUiEntity(from: FavoritedMovieUiEntity): FavoritedMovieDomainEntity {
        return FavoritedMovieDomainEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }

    override fun mapToUiEntity(from: FavoritedMovieDomainEntity): FavoritedMovieUiEntity {
        return FavoritedMovieUiEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }
}
