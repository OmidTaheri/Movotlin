package ir.omidtaheri.genrelist.entity

data class MovieUiEntity(
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double
)
