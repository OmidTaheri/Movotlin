package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.domain.entity.FavoritedMovieDomainEntity
import javax.inject.Inject

class FavoritedMovieEntityDomainDataMapper @Inject constructor() :
    Domain_Data_Mapper<FavoritedMovieDataEntity, FavoritedMovieDomainEntity> {
    override fun mapFromDataEntity(from: FavoritedMovieDataEntity): FavoritedMovieDomainEntity {
        return FavoritedMovieDomainEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }

    override fun mapToDataEntity(from: FavoritedMovieDomainEntity): FavoritedMovieDataEntity {
        return FavoritedMovieDataEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }
}
