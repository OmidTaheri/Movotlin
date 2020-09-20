package ir.omidtaheri.remote.mapper

interface DataToRequestEntityMapper<DataEntity, DTO> {

    fun mapToDTO(from: DataEntity): DTO
}
