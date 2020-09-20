package ir.omidtaheri.genrelist.entity

data class MultiMovieUiEntity(
    val page: Int,
    val results: List<MovieUiEntity>,
    val totalPages: Int,
    val totalResults: Int
)
