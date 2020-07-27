package ir.omidtaheri.data.entity

data class FavoritedMovieDataEntity(
    val backdrop_path: String?,
    val id: Int,
    val poster_path: String?,
    val title: String,
    val vote_average: Double
)