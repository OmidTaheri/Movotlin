package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.domain.entity.MovieDomainEntity
import javax.inject.Inject

class MovieEntityDomainDataMapper @Inject constructor() :
    Domain_Data_Mapper<MovieDataEntity, MovieDomainEntity> {
    override fun mapFromDataEntity(from: MovieDataEntity): MovieDomainEntity {

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

    override fun mapToDataEntity(from: MovieDomainEntity): MovieDataEntity {

        return MovieDataEntity(
            false,
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
