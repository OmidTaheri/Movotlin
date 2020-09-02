package ir.omidtaheri.mainpagetv.mapper

import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.mainpagetv.entity.Backdrop
import ir.omidtaheri.mainpagetv.entity.MovieImageUiEntity
import ir.omidtaheri.mainpagetv.entity.Poster
import javax.inject.Inject

class MovieImageEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<MovieImageUiEntity, MovieImageDomainEntity> {

    override fun mapFromUiEntity(from: MovieImageUiEntity): MovieImageDomainEntity {

        return MovieImageDomainEntity(from.backdrops.map {
            ir.omidtaheri.domain.entity.Backdrop(it.aspectRatio, it.filePath, it.height, it.width)
        }, from.id, from.posters.map {
            ir.omidtaheri.domain.entity.Poster(it.aspectRatio, it.filePath, it.height, it.width)
        })
    }

    override fun mapToUiEntity(from: MovieImageDomainEntity): MovieImageUiEntity {
        return MovieImageUiEntity(from.backdrops.map {
            Backdrop(it.aspectRatio, it.filePath, it.height, it.width)
        }, from.id, from.posters.map {
            Poster(it.aspectRatio, it.filePath, it.height, it.width)
        })
    }
}
