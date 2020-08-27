package ir.omidtaheri.remote.mapper

interface DataToRequest_EntityMapper<DataEntity, DTO> {

    fun mapToDTO(from: DataEntity): DTO
}
