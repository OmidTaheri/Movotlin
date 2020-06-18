package ir.omidtaheri.data.entity

data class MovieDataEntity(
    val id: Long,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val isFavorite: Boolean
)