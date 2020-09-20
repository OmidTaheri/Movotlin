package ir.omidtaheri.local.mapper

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.local.entity.MovieLocalEntity
import javax.inject.Inject

class MovieEntityDataLocalMapper @Inject constructor() :
    DataLocalEntityMapper<FavoritedMovieDataEntity, MovieLocalEntity> {

    override fun mapFromDataEntity(from: FavoritedMovieDataEntity): MovieLocalEntity {
        return MovieLocalEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }

    override fun mapToDataEntity(from: MovieLocalEntity): FavoritedMovieDataEntity {
        return FavoritedMovieDataEntity(
            from.backdropPath,
            from.id,
            from.posterPath,
            from.title,
            from.voteAverage
        )
    }
}
