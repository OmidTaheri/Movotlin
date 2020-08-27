package ir.omidtaheri.domain.entity

data class MultiMovieDomainEntity(
    val page: Int,
    val results: List<MovieDomainEntity>,
    val totalPages: Int,
    val totalResults: Int
)
