package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.mainpage.entity.Backdrop
import ir.omidtaheri.mainpage.entity.MovieImageUiEntity
import ir.omidtaheri.mainpage.entity.Poster
import javax.inject.Inject

class MovieImageEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<MovieImageUiEntity, MovieImageDomainEntity> {

    override fun mapFromUiEntity(from: MovieImageUiEntity): MovieImageDomainEntity {

        return MovieImageDomainEntity(from.backdrops.map {
            ir.omidtaheri.domain.entity.Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }, from.id, from.posters.map {
            ir.omidtaheri.domain.entity.Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        })
    }

    override fun mapToUiEntity(from: MovieImageDomainEntity): MovieImageUiEntity {
        return MovieImageUiEntity(from.backdrops.map {
            Backdrop(it.aspect_ratio, it.file_path, it.height, it.width)
        }, from.id, from.posters.map {
            Poster(it.aspect_ratio, it.file_path, it.height, it.width)
        })
    }
}
