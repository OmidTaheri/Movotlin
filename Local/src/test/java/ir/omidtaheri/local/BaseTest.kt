package ir.omidtaheri.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ir.omidtaheri.local.database.AppDatabase
import org.junit.After
import org.junit.Before

open class BaseTest {

    lateinit var db: AppDatabase

    @Before
    open fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // only for testing
            .build()
    }

    @After
    open fun tearDown() {
        db.close()
    }
}
