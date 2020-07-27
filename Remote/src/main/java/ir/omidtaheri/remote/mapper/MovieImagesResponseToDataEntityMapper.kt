package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.data.entity.MovieDetailDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.remote.entity.response.*
import javax.inject.Inject

class MovieImagesResponseToDataEntityMapper @Inject constructor() :
    ResponseToData_EntityMapper<MovieImagesResponseRemoteEntity, MovieImageDataEntity> {
    override fun mapFromDTO(from: MovieImagesResponseRemoteEntity): MovieImageDataEntity {

        return MovieImageDataEntity(
            mapFromBackdropsDTO(from.backdrops),
            from.id,
            mapFromPosterDTO(from.posters)
        )
    }


    fun mapFromBackdropsDTO(dto_backdrops_list: List<Backdrop>): List<ir.omidtaheri.data.entity.Backdrop> {

        return dto_backdrops_list.map {

            ir.omidtaheri.data.entity.Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }
    }

    fun mapFromPosterDTO(dto_poster_list: List<Poster>): List<ir.omidtaheri.data.entity.Poster> {

        return dto_poster_list.map {
            ir.omidtaheri.data.entity.Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        }
    }

}