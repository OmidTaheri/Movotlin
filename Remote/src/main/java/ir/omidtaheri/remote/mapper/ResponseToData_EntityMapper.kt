package ir.omidtaheri.remote.mapper

interface ResponseToData_EntityMapper<DTO, DataEntity> {
    fun mapFromDTO(from: DTO): DataEntity
}