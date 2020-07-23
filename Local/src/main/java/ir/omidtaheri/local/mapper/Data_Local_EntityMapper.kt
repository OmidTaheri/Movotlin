package ir.omidtaheri.local.mapper

interface Data_Local_EntityMapper <DataEntity, LocalEntity> {

    fun mapFromDataEntity(from: DataEntity): LocalEntity
    fun mapToDataEntity(from: LocalEntity): DataEntity

}