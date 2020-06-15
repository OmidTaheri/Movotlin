package dependencies

object TestAndroidDependencies {


    const val ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"

    const val ESPRESSO_CONTRIB =
        "androidx.test.espresso:espresso-contrib:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"

    const val ESPRESSO_INTENTS =
        "androidx.test.espresso:espresso-intents:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"

    const val ESPRESSO_ACCESSIBILITY =
        "androidx.test.espresso:espresso-accessibility:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"

    const val ESPRESSO_CONCURRENT =
        "androidx.test.espresso.idling:idling-concurrent:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"

    const val ESPRESSO_RESOURCE =
        "androidx.test.espresso:espresso-idling-resource:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"


    const val HAMCREST =
        "org.hamcrest:hamcrest-library:${BuildDependenciesVersions.TestDependenciesVersions.HAMCREST}"

    const val UIAUTOMATOR =
        "androidx.test.uiautomator:uiautomator:${BuildDependenciesVersions.TestDependenciesVersions.UIAUTOMATOR}"


    const val RUNNER =
        "androidx.test:runner:${BuildDependenciesVersions.TestDependenciesVersions.TEST}"
    const val RULES =
        "androidx.test:rules:${BuildDependenciesVersions.TestDependenciesVersions.TEST}"


    const val MOCKK =
        "io.mockk:mockk-android:${BuildDependenciesVersions.TestDependenciesVersions.MOCKK}"

}