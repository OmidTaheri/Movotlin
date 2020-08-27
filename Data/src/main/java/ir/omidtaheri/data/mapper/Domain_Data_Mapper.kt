package ir.omidtaheri.data.mapper

interface Domain_Data_Mapper<DataEntity, DomainEntity> {

    fun mapFromDataEntity(from: DataEntity): DomainEntity
    fun mapToDataEntity(from: DomainEntity): DataEntity
}
