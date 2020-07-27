package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.remote.entity.response.MovieDetailResponseRemoteEntity
import javax.inject.Inject

class MovieDetailResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<MovieDetailResponseRemoteEntity, MovieDetailDataEntity> {
    override fun mapFromDTO(from: MovieDetailResponseRemoteEntity): MovieDetailDataEntity {

        return MovieDetailDataEntity(
            from.backdrop_path,
            from.genres.map { GenreDataEntity(it.id, it.name) },
            from.id,
            from.overview,
            from.poster_path, from.tagline, from.title, from.video, from.vote_average
        )
    }
}