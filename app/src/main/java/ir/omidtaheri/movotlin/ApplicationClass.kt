package ir.omidtaheri.movotlin

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent
import ir.omidtaheri.uibase.enableDarkMode
import ir.omidtaheri.uibase.getDarkModeStatus


class ApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        this.applicationComponent =
            DaggerApplicationComponent.factory()
                .create(this, "Movotlin", BuildConfig.BASE_URL, BuildConfig.API_KEY)

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
