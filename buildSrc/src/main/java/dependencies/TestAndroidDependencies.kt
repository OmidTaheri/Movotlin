package dependencies
object TestAndroidDependencies {

    const val LEAKCANARY =
        "com.squareup.leakcanary:leakcanary-android-instrumentation:${BuildDependenciesVersions.DependenciesVersions.LEAKCANARY}"
    const val MOCKITO =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${BuildDependenciesVersions.TestDependenciesVersions.MOCKITO}"
    const val ESPRESSO =
        "androidx.test.espresso:espresso-core:${BuildDependenciesVersions.TestDependenciesVersions.ESPRESSO}"
    const val RUNNER = "androidx.test:runner:${BuildDependenciesVersions.TestDependenciesVersions.TEST}"
    const val RULES = "androidx.test:rules:${BuildDependenciesVersions.TestDependenciesVersions.TEST}"
    const val JUNIT = "androidx.test.ext:junit:${BuildDependenciesVersions.TestDependenciesVersions.EXT}"
    const val FRAGMENT_TEST =
        "androidx.fragment:fragment-testing:${BuildDependenciesVersions.TestDependenciesVersions.FRAGMENT_TEST}"
    const val PLAY_CORE =
        "com.google.android.play:core:${BuildDependenciesVersions.DependenciesVersions.PLAY_CORE}"


}