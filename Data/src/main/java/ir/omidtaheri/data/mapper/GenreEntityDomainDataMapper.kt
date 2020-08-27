package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.GenreDataEntity
import ir.omidtaheri.domain.entity.GenreDomainEntity
import javax.inject.Inject

class GenreEntityDomainDataMapper @Inject constructor() : Domain_Data_Mapper<GenreDataEntity, GenreDomainEntity> {

    override fun mapFromDataEntity(from: GenreDataEntity): GenreDomainEntity {

        return GenreDomainEntity(from.id, from.name)
    }

    override fun mapToDataEntity(from: GenreDomainEntity): GenreDataEntity {

        return GenreDataEntity(from.id, from.name)
    }
}
