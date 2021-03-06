package ir.omidtaheri.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieLocalEntity(
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double
)
