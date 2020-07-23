package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.mainpage.entity.MovieUiEntity

class MovieEntityUiDomainMapper : Ui_Domain_Mapper<MovieUiEntity, MovieDomainEntity> {

    override fun mapFromUiEntity(from: MovieUiEntity): MovieDomainEntity {

        return MovieDomainEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }

    override fun mapToUiEntity(from: MovieDomainEntity): MovieUiEntity {
        return MovieUiEntity(from.id, from.title, from.rating, from.posterPath, from.isFavorite)
    }
}