package ir.omidtaheri.data.mapper

interface Mapper<DataEntity, DomainEntity> {

    fun mapFromDataEntity(from: DataEntity): DomainEntity
    fun mapToDataEntity(from: DomainEntity): DataEntity

}