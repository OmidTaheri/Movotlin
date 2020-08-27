package ir.omidtaheri.remote.entity.response

data class MovieDetailResponseRemoteEntity(
    val adult: Boolean,
    val backdrop_path: String?,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double
)
