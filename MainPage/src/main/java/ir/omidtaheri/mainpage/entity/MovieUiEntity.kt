package ir.omidtaheri.mainpage.entity

data class MovieUiEntity(
    val id: Long,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val isFavorite: Boolean
)
