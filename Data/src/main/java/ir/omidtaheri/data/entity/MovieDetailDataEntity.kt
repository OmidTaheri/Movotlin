package ir.omidtaheri.data.entity

data class MovieDetailDataEntity(
    val backdrop_path: String?,
    val genres: List<GenreDataEntity>,
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double
)