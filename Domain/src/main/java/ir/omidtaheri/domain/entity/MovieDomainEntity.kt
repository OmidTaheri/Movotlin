package ir.omidtaheri.domain.entity

data class MovieDomainEntity(
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
