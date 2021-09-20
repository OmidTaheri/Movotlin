package ir.omidtaheri.movotlin.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ir.omidtaheri.local.dao.MovieDao
import ir.omidtaheri.local.database.AppDatabase
import javax.inject.Named
import javax.inject.Singleton

@Module
object LocalModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context,@Named("dbName") dbName: String): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.getMovieDao()
    }
}
