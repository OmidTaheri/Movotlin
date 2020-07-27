package ir.omidtaheri.data.entity

data class MultiMovieDataEntity(
    val page: Int,
    val results: List<MovieDataEntity>,
    val total_pages: Int,
    val total_results: Int
)