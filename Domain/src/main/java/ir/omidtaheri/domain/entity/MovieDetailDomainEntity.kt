package ir.omidtaheri.domain.entity

data class MovieDetailDomainEntity(
    val backdropPath: String?,
    val genres: List<GenreDomainEntity>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
