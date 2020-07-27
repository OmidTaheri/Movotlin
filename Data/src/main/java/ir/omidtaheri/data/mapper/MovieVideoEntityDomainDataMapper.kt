package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.*
import ir.omidtaheri.domain.entity.MovieDomainEntity
import ir.omidtaheri.domain.entity.MovieImageDomainEntity
import ir.omidtaheri.domain.entity.MovieVideoDomainEntity
import javax.inject.Inject

class MovieVideoEntityDomainDataMapper @Inject constructor() :
    Domain_Data_Mapper<MovieVideoDataEntity, MovieVideoDomainEntity> {


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