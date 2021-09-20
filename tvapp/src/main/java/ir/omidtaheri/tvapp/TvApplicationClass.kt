package ir.omidtaheri.tvapp

import androidx.multidex.MultiDexApplication
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent

class TvApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent =
            DaggerApplicationComponent.factory()
                .create(this, "Movotlin", BuildConfig.BASE_URL, BuildConfig.API_KEY)


        applicationComponent.inject(this)
    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }
}
