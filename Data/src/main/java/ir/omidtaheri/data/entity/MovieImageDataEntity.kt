package ir.omidtaheri.data.entity

data class MovieImageDataEntity(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)

data class Backdrop(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val width: Int
)

data class Poster(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val width: Int
)
