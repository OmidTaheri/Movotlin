package ir.omidtaheri.favorite.entity

data class FavoritedMovieUiEntity(
    val backdropPath: String?,
    val id: Int,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double
)
