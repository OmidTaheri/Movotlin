package ir.omidtaheri.data.entity

data class MovieDetailDataEntity(
    val backdropPath: String?,
    val genres: List<GenreDataEntity>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
