package ir.omidtaheri.movotlin

import androidx.fragment.app.FragmentManager
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.movotlin.di.components.ApplicationComponent
import ir.omidtaheri.movotlin.di.components.DaggerApplicationComponent
import ir.omidtaheri.uibase.enableDarkMode
import ir.omidtaheri.uibase.getDarkModeStatus


class ApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent

    private lateinit var myChildFragmentManager0: FragmentManager
    private lateinit var myChildFragmentManager1: FragmentManager
    private lateinit var myChildFragmentManager2: FragmentManager
    private lateinit var myChildFragmentManager3: FragmentManager

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

    fun setFragManager0(fragManager: FragmentManager) {
        myChildFragmentManager0 = fragManager
    }

    fun setFragManager1(fragManager: FragmentManager) {
        myChildFragmentManager1 = fragManager
    }

    fun setFragManager2(fragManager: FragmentManager) {
        myChildFragmentManager2 = fragManager
    }

    fun setFragManager3(fragManager: FragmentManager) {
        myChildFragmentManager3 = fragManager
    }

    fun getFragManager0() = myChildFragmentManager0
    fun getFragManager1() = myChildFragmentManager1
    fun getFragManager2() = myChildFragmentManager2
    fun getFragManager3() = myChildFragmentManager3


}
