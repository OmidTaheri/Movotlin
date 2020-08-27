package ir.omidtaheri.remote.entity.response

data class MultiMovieResponseRemoteEntity(
    val page: Int,
    val results: List<MultiMovieResult>,
    val total_pages: Int,
    val total_results: Int
)
