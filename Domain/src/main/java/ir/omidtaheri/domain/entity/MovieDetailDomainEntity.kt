package ir.omidtaheri.domain.entity

data class MovieDetailDomainEntity(
    val backdrop_path: String?,
    val genres: List<GenreDomainEntity>,
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double
)
