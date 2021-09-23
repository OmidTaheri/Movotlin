package ir.omidtaheri.detailpage.mapper

import ir.omidtaheri.detailpage.entity.GenreUiEntity
import ir.omidtaheri.domain.entity.GenreDomainEntity
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
