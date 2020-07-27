package ir.omidtaheri.mainpage.entity

data class MultiMovieUiEntity(
    val page: Int,
    val results: List<MovieUiEntity>,
    val total_pages: Int,
    val total_results: Int
)