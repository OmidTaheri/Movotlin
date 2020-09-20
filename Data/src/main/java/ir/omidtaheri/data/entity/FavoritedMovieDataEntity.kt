package ir.omidtaheri.data.entity

data class FavoritedMovieDataEntity(
    val backdropPath: String?,
    val id: Int,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double
)
