package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.domain.entity.MovieDetailDomainEntity
import javax.inject.Inject

class MovieDetailEntityDomainDataMapper @Inject constructor(val genreEntityDomainDataMapper: GenreEntityDomainDataMapper) :
    Domain_Data_Mapper<MovieDetailDataEntity, MovieDetailDomainEntity> {
    override fun mapFromDataEntity(from: MovieDetailDataEntity): MovieDetailDomainEntity {

        return MovieDetailDomainEntity(
            from.backdrop_path,
            from.genres.map {
                genreEntityDomainDataMapper.mapFromDataEntity(it)
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

    override fun mapToDataEntity(from: MovieDetailDomainEntity): MovieDetailDataEntity {

        return MovieDetailDataEntity(
            from.backdrop_path,
            from.genres.map {
                genreEntityDomainDataMapper.mapToDataEntity(it)
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
