package ir.omidtaheri.remote.entity.response

data class GenreResponseRemoteEntity(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
