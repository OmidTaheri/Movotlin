package ir.omidtaheri.domain.entity

data class FavoritedMovieDomainEntity(
    val backdropPath: String?,
    val id: Int,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double
)
