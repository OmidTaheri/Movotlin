package ir.omidtaheri.search.mapper


import ir.omidtaheri.domain.entity.GenreDomainEntity
import ir.omidtaheri.search.entity.GenreUiEntity
import javax.inject.Inject

class GenreEntityUiDomainMapper @Inject constructor():Ui_Domain_Mapper<GenreUiEntity, GenreDomainEntity>{
    override fun mapFromUiEntity(from: GenreUiEntity): GenreDomainEntity {
        return GenreDomainEntity(from.id, from.name)
    }

    override fun mapToUiEntity(from: GenreDomainEntity): GenreUiEntity {
       return GenreUiEntity(from.id, from.name)
    }
}