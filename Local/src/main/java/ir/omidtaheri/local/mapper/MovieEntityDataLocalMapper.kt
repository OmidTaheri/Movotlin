package ir.omidtaheri.local.mapper

import ir.omidtaheri.data.entity.FavoritedMovieDataEntity
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.local.entity.MovieLocalEntity
import javax.inject.Inject

class MovieEntityDataLocalMapper @Inject constructor() :
    Data_Local_EntityMapper<FavoritedMovieDataEntity, MovieLocalEntity> {

    override fun mapFromDataEntity(from: FavoritedMovieDataEntity): MovieLocalEntity {
        return MovieLocalEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }

    override fun mapToDataEntity(from: MovieLocalEntity): FavoritedMovieDataEntity {
        return FavoritedMovieDataEntity(
            from.backdrop_path,
            from.id,
            from.poster_path,
            from.title,
            from.vote_average
        )
    }


}