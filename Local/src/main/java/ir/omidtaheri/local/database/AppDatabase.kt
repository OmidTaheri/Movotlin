package ir.omidtaheri.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.entity.MovieLocalEntity

@Database(entities = [MovieLocalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}
