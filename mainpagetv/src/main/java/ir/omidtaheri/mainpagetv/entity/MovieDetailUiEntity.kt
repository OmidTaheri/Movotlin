package ir.omidtaheri.mainpagetv.entity

data class MovieDetailUiEntity(
    val backdropPath: String?,
    val genres: List<GenreUiEntity>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
