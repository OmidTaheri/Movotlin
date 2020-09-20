package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.mainpage.entity.GenreUiEntity
import javax.inject.Inject

class GenreEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<GenreUiEntity, GenreDomainEntity> {
    override fun mapFromUiEntity(from: GenreUiEntity): GenreDomainEntity {
        return GenreDomainEntity(from.id, from.name)
    }

    override fun mapToUiEntity(from: GenreDomainEntity): GenreUiEntity {
        return GenreUiEntity(from.id, from.name)
    }
}
