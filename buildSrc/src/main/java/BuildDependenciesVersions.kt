object BuildDependenciesVersions {


    object DependenciesVersions {

        const val RETROFIT = "2.8.1"
        const val RXJAVA = "2.2.19"
        const val RXKotlin = "2.4.0"
        const val RXANDROID = "2.0.2"
        const val GLIDE = "4.11.0"
        const val DAGGER = "2.25.3"
        const val KOTLIN = "1.3.72"
        const val COROUTINES = "1.3.3"
        const val CORE_KTX = "1.3.0"
        const val FRAGMENT_KTX = "1.2.0-alpha02"
        const val LIVEDATA_KTX = "2.2.0"
        const val PLAY_CORE = "1.6.4"
        const val LEAKCANARY = "2.4"
        const val STETHO = "1.5.1"
        const val DATABINDING = "3.1.4"
        const val AUTO_SERVICE = "1.0-rc4"
        const val ANOTATION = "1.1.0"
        const val JAVAX_INJECT = "1"
        const val JAVAX_ANNOTATION = "1.0"
        const val MULTIDEX = "2.0.1"
    }

    object JetpackDependenciesVersions {
        const val LIFECYCLE = "2.2.0"
        const val FUTURES = "1.0.0"
        const val ROOM = "2.2.5"
        const val NAVIGATION = "2.3.0"
        const val PAGING = "3.0.0-alpha03"
        const val WORKERMANAGER = "2.0.1"
    }


    object UiDependenciesVersions {
        const val APPCOMPAT = "1.1.0"
        const val MATERIAL = "1.3.0-alpha01"
        const val RECYCLE_VIEW = "1.2.0-alpha03"
        const val CONSTRAINT_LAYOUT = "2.0.0-beta7"
        const val CARDVIEW = "1.0.0"
        const val VIEWPAGER2 = "1.0.0"
        const val VECTORE_DRAWABLE = "1.1.0"
        const val COORDINATORLAYOUT = "1.1.0"
        const val ANIMATED_VECTORE_DRAWABLE = "1.1.0"
        const val LEANBACK = "1.0.0"
    }


    object TestDependenciesVersions {
        const val TEST = "1.1.0"
        const val EXT = "1.0.0"
        const val TRUTH = "0.42"
        const val ARCH_CORE = "2.1.0"
        const val JUNIT = "4.12"
        const val ROBOELECTRIC = "4.3"
        const val MOCKITO = "3.0.0"
        const val MOCKK = "1.10.0"
        const val ESPRESSO = "3.1.0"
        const val HAMCREST = "1.3"
        const val UIAUTOMATOR = "2.2.0"
        const val FRAGMENT_TEST = "1.2.0-alpha02"
        const val MOCK_WEB_SERVER = "4.2.2"
    }


    object GradlePluginsVersions {
        const val GRADLE_ANDROID_PLUGIN = "4.0.0"
        const val GRADLE_KOTLIN_PLUGIN = "1.3.72"
        const val SAFEARGS = JetpackDependenciesVersions.NAVIGATION
    }


    object PluginsVersions {
        const val KTLINT_PLUGIN = "9.2.1"
        const val DETEKT_PLUGIN = "1.10.0-RC1"
        const val DOKKA_PLUGIN = "0.10.1"
        const val SAFEARGS = JetpackDependenciesVersions.NAVIGATION
    }
}