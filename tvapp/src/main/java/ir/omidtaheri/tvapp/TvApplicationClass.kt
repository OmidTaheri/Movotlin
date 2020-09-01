package ir.omidtaheri.tvapp

import androidx.multidex.MultiDexApplication
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.LocalModule
import ir.omidtaheri.movotlin.di.modules.RemoteModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule


class TvApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        this.applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .localModule(LocalModule("Movotlin"))
                .remoteModule(RemoteModule(BuildConfig.BASE_URL, BuildConfig.API_KEY))
                .repositoryModule(RepositoryModule())
                .build()

        applicationComponent.inject(this)
    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }
}
