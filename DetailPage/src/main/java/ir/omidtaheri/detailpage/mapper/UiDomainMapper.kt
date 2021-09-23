package ir.omidtaheri.detailpage.mapper

interface UiDomainMapper<UiEntity, DomainEntity> {

    fun mapFromUiEntity(from: UiEntity): DomainEntity
    fun mapToUiEntity(from: DomainEntity): UiEntity
}
