package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.remote.entity.response.Backdrop
import ir.omidtaheri.remote.entity.response.MovieImagesResponseRemoteEntity
import ir.omidtaheri.remote.entity.response.Poster
import javax.inject.Inject

class MovieImagesResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<MovieImagesResponseRemoteEntity, MovieImageDataEntity> {
    override fun mapFromDTO(from: MovieImagesResponseRemoteEntity): MovieImageDataEntity {

        return MovieImageDataEntity(
            mapFromBackdropsDTO(from.backdrops),
            from.id,
            mapFromPosterDTO(from.posters)
        )
    }

    fun mapFromBackdropsDTO(dtoBackdropsList: List<Backdrop>): List<ir.omidtaheri.data.entity.Backdrop> {

        return dtoBackdropsList.map {

            ir.omidtaheri.data.entity.Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }
    }

    fun mapFromPosterDTO(dtoPosterList: List<Poster>): List<ir.omidtaheri.data.entity.Poster> {

        return dtoPosterList.map {
            ir.omidtaheri.data.entity.Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        }
    }
}
