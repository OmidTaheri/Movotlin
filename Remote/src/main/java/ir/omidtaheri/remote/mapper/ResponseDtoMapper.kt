package ir.omidtaheri.remote.mapper

interface ResponseDtoMapper<DTO, DataEntity> {
    fun mapFromDTO(from: DTO): DataEntity
}