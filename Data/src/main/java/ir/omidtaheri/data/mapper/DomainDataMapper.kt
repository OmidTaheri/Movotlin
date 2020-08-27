package ir.omidtaheri.data.mapper

interface DomainDataMapper<DataEntity, DomainEntity> {

    fun mapFromDataEntity(from: DataEntity): DomainEntity
    fun mapToDataEntity(from: DomainEntity): DataEntity
}
