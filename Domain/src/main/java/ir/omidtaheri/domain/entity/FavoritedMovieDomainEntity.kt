package ir.omidtaheri.domain.entity

data class FavoritedMovieDomainEntity(
    val backdrop_path: String?,
    val id: Int,
    val poster_path: String?,
    val title: String,
    val vote_average: Double
)