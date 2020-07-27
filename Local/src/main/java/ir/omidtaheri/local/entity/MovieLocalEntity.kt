package ir.omidtaheri.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieLocalEntity(
    val backdrop_path: String?,
    @PrimaryKey
    val id: Int,
    val poster_path: String?,
    val title: String,
    val vote_average: Double
)




