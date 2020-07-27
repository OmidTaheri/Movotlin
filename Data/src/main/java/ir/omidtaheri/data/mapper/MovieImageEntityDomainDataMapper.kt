package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.Backdrop
import ir.omidtaheri.data.entity.MovieDataEntity
import ir.omidtaheri.data.entity.MovieImageDataEntity
import ir.omidtaheri.data.entity.Poster
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import javax.inject.Inject

class MovieImageEntityDomainDataMapper @Inject constructor() :
    Domain_Data_Mapper<MovieImageDataEntity, MovieImageDomainEntity> {

    override fun mapFromDataEntity(from: MovieImageDataEntity): MovieImageDomainEntity {
        return MovieImageDomainEntity(from.backdrops.map {
            ir.omidtaheri.domain.entity.Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }, from.id, from.posters.map {
            ir.omidtaheri.domain.entity.Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        })
    }

    override fun mapToDataEntity(from: MovieImageDomainEntity): MovieImageDataEntity {
        return MovieImageDataEntity(from.backdrops.map {
            Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }, from.id, from.posters.map {
            Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        })
    }

}