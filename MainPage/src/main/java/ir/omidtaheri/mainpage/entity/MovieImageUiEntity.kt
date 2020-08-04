package ir.omidtaheri.mainpage.entity

data class MovieImageUiEntity(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)

data class Backdrop(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val width: Int
)

data class Poster(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val width: Int
)