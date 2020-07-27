package ir.omidtaheri.mainpage.entity


data class MovieDetailUiEntity (
    val backdrop_path: String?,
    val genres: List<GenreUiEntity>,
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double
)