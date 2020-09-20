package ir.omidtaheri.movotlin.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.database.AppDatabase

@Module
class LocalModule(val dbName: String) {

    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.getMovieDao()
    }
}
