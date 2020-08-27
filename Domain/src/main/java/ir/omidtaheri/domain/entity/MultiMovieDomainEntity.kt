package ir.omidtaheri.domain.entity

data class MultiMovieDomainEntity(
    val page: Int,
    val results: List<MovieDomainEntity>,
    val total_pages: Int,
    val total_results: Int
)
