package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import ir.omidtaheri.mainpage.entity.MovieDetailUiEntity
import javax.inject.Inject

class MovieDetailEntityUiDomainMapper @Inject constructor(val genreEntityUiDomainMapper: GenreEntityUiDomainMapper) :
    Ui_Domain_Mapper<MovieDetailUiEntity, MovieDetailDomainEntity> {
    override fun mapFromUiEntity(from: MovieDetailUiEntity): MovieDetailDomainEntity {
        return MovieDetailDomainEntity(
            from.backdrop_path,
            from.genres.map {
                genreEntityUiDomainMapper.mapFromUiEntity(it)
            },
            from.id,
            from.overview,
            from.poster_path,
            from.tagline,
            from.title,
            from.video,
            from.vote_average
        )
    }

    override fun mapToUiEntity(from: MovieDetailDomainEntity): MovieDetailUiEntity {

        return MovieDetailUiEntity(
            from.backdrop_path,
            from.genres.map {
                genreEntityUiDomainMapper.mapToUiEntity(it)
            },
            from.id,
            from.overview,
            from.poster_path,
            from.tagline,
            from.title,
            from.video,
            from.vote_average
        )
    }
}
