package ir.omidtaheri.movotlin

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.LocalModule
import ir.omidtaheri.movotlin.di.modules.RemoteModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule
import ir.omidtaheri.uibase.enableDarkMode
import ir.omidtaheri.uibase.getDarkModeStatus


class ApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        this.applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .localModule(LocalModule("Movotlin"))
                .remoteModule(RemoteModule(BuildConfig.BASE_URL, BuildConfig.API_KEY))
                .repositoryModule(RepositoryModule())
                .build()

        applicationComponent.inject(this)

        if (getDarkModeStatus(applicationContext)) {
            enableDarkMode(true)
        } else {
            enableDarkMode(false)
        }

    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }


}
