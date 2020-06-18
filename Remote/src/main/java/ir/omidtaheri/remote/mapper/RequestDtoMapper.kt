package ir.omidtaheri.remote.mapper

interface RequestDtoMapper<DataEntity, DTO> {

    fun mapToDTO(from: DataEntity): DTO

}