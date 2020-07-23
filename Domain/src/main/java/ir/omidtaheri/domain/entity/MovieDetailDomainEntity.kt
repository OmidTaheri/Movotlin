package ir.omidtaheri.domain.entity

data class MovieDetailDomainEntity(
    val id: Long, val title: String, val voteAverage: Double,
    val posterPath: String, val backdropPath: String, val overview: String,
    val tagline: String, val isFavorite: Boolean
)