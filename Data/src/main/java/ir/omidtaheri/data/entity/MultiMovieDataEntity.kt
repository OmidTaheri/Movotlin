package ir.omidtaheri.data.entity

data class MultiMovieDataEntity(
    val page: Int,
    val results: List<MovieDataEntity>,
    val totalPages: Int,
    val totalResults: Int
)
