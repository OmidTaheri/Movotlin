package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MultiMovieDataEntity
import ir.omidtaheri.domain.entity.MultiMovieDomainEntity
import javax.inject.Inject

class MultiMovieEntityDomainDataMapper @Inject constructor(val movieEntityDataDomainMapper: MovieEntityDomainDataMapper) :
    Domain_Data_Mapper<MultiMovieDataEntity, MultiMovieDomainEntity> {

    override fun mapFromDataEntity(from: MultiMovieDataEntity): MultiMovieDomainEntity {

        return MultiMovieDomainEntity(from.page, from.results.map {
            movieEntityDataDomainMapper.mapFromDataEntity(it)
        }, from.total_pages, from.total_results)
    }

    override fun mapToDataEntity(from: MultiMovieDomainEntity): MultiMovieDataEntity {
        return MultiMovieDataEntity(from.page, from.results.map {
            movieEntityDataDomainMapper.mapToDataEntity(it)
        }, from.total_pages, from.total_results)
    }
}
