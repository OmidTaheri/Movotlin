package ir.omidtaheri.movotlin

import android.app.Application
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent
import ir.omidtaheri.movotlin.di.modules.ApplicationModule
import ir.omidtaheri.movotlin.di.modules.LocalModule
import ir.omidtaheri.movotlin.di.modules.RemoteModule
import ir.omidtaheri.movotlin.di.modules.RepositoryModule

class ApplicationClass : Application(), ApplicationComponentProvider {


    lateinit var ApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        this.ApplicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .localModule(LocalModule("Movotlin"))
                .remoteModule(RemoteModule(BuildConfig.BASE_URL,"123456"))
                .repositoryModule(RepositoryModule())
                .build()

        ApplicationComponent.inject(this)

    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return ApplicationComponent
    }

}