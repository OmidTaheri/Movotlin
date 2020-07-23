package ir.omidtaheri.mainpage.entity

data class MovieDetailUiEntity (
    val id: Long, val title: String, val voteAverage: Double,
    val posterPath: String, val backdropPath: String, val overview: String,
    val tagline: String, val isFavorite: Boolean
)