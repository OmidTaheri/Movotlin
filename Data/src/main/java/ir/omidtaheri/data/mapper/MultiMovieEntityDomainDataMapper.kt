package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import javax.inject.Inject

class MultiMovieEntityDomainDataMapper @Inject constructor(private val movieEntityDataDomainMapper: MovieEntityDomainDataMapper) :
    DomainDataMapper<MultiMovieDataEntity, MultiMovieDomainEntity> {

    override fun mapFromDataEntity(from: MultiMovieDataEntity): MultiMovieDomainEntity {

        return MultiMovieDomainEntity(from.page, from.results.map {
            movieEntityDataDomainMapper.mapFromDataEntity(it)
        }, from.totalPages, from.totalResults)
    }

    override fun mapToDataEntity(from: MultiMovieDomainEntity): MultiMovieDataEntity {
        return MultiMovieDataEntity(from.page, from.results.map {
            movieEntityDataDomainMapper.mapToDataEntity(it)
        }, from.totalPages, from.totalResults)
    }
}
