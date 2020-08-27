package ir.omidtaheri.favorite.mapper

interface Ui_Domain_Mapper<UiEntity, DomainEntity> {

    fun mapFromUiEntity(from: UiEntity): DomainEntity
    fun mapToUiEntity(from: DomainEntity): UiEntity
}
