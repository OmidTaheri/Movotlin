package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity

class MovieEntityDomainDataMapper : Domain_Data_Mapper<MovieDataEntity, MovieDomainEntity> {
    override fun mapFromDataEntity(from: MovieDataEntity): MovieDomainEntity {

        return MovieDomainEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }

    override fun mapToDataEntity(from: MovieDomainEntity): MovieDataEntity {

        return MovieDataEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }
}