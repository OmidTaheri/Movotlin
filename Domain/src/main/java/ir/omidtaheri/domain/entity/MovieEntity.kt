package ir.omidtaheri.domain.entity

data class MovieEntity(
    val id: Long,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val isFavorite: Boolean
)