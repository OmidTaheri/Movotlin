package ir.omidtaheri.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieLocalEntity(
    val id: Long,
    val title: String,
    val rating: Double,
    val picture: String
)




