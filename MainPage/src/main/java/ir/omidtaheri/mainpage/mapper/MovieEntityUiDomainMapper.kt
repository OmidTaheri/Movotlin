package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.mainpage.entity.MovieUiEntity
import javax.inject.Inject

class MovieEntityUiDomainMapper @Inject constructor() :
    Ui_Domain_Mapper<MovieUiEntity, MovieDomainEntity> {

    override fun mapFromUiEntity(from: MovieUiEntity): MovieDomainEntity {

        return MovieDomainEntity(
            from.backdrop_path,
            from.genre_ids,
            from.id,
            from.overview,
            from.poster_path,
            from.release_date,
            from.title,
            from.video,
            from.vote_average
        )
    }

    override fun mapToUiEntity(from: MovieDomainEntity): MovieUiEntity {
        return MovieUiEntity(
            from.backdrop_path,
            from.genre_ids,
            from.id,
            from.overview,
            from.poster_path,
            from.release_date,
            from.title,
            from.video,
            from.vote_average
        )
    }
}