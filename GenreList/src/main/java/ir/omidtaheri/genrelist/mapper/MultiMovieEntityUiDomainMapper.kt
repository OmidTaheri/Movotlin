package ir.omidtaheri.genrelist.mapper

import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import ir.omidtaheri.genrelist.entity.MultiMovieUiEntity
import javax.inject.Inject

class MultiMovieEntityUiDomainMapper @Inject constructor(val movieEntityUiDomainMapper: MovieEntityUiDomainMapper) :
    UiDomainMapper<MultiMovieUiEntity, MultiMovieDomainEntity> {

    override fun mapFromUiEntity(from: MultiMovieUiEntity): MultiMovieDomainEntity {
        return MultiMovieDomainEntity(from.page, from.results.map {
            movieEntityUiDomainMapper.mapFromUiEntity(it)
        }, from.totalPages, from.totalResults)
    }

    override fun mapToUiEntity(from: MultiMovieDomainEntity): MultiMovieUiEntity {
        return MultiMovieUiEntity(from.page, from.results.map {
            movieEntityUiDomainMapper.mapToUiEntity(it)
        }, from.totalPages, from.totalResults)
    }
}
