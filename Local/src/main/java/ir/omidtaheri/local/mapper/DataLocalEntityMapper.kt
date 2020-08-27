package ir.omidtaheri.local.mapper

interface DataLocalEntityMapper <DataEntity, LocalEntity> {

    fun mapFromDataEntity(from: DataEntity): LocalEntity
    fun mapToDataEntity(from: LocalEntity): DataEntity
}
