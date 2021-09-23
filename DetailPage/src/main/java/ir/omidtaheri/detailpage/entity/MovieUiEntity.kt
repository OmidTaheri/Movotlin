package ir.omidtaheri.detailpage.entity

data class MovieUiEntity(
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
