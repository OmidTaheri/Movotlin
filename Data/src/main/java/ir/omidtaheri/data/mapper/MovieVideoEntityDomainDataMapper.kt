package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.MovieVideoDataEntity
import ir.omidtaheri.data.entity.Result
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import javax.inject.Inject

class MovieVideoEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<MovieVideoDataEntity, MovieVideoDomainEntity> {

    override fun mapFromDataEntity(from: MovieVideoDataEntity): MovieVideoDomainEntity {
        return MovieVideoDomainEntity(from.id, from.results.map {
            ir.omidtaheri.domain.entity.Result(it.id, it.key, it.name, it.site, it.size, it.type)
        })
    }

    override fun mapToDataEntity(from: MovieVideoDomainEntity): MovieVideoDataEntity {
        return MovieVideoDataEntity(from.id, from.results.map {
            Result(it.id, it.key, it.name, it.site, it.size, it.type)
        })
    }
}
