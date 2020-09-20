package ir.omidtaheri.domain.entity

data class MovieVideoDomainEntity(
    val id: Int,
    val results: List<Result>
)

data class Result(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)
