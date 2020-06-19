package ir.omidtaheri.domain.entity

data class MovieDomainEntity(
    val id: Long,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val isFavorite: Boolean
)