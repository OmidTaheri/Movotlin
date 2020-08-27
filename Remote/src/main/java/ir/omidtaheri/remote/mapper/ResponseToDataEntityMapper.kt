package ir.omidtaheri.remote.mapper

interface ResponseToDataEntityMapper<DTO, DataEntity> {
    fun mapFromDTO(from: DTO): DataEntity
}
