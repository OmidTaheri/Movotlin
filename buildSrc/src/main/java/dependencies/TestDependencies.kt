package dependencies
object TestDependencies {

    const val JUNIT = "junit:junit:${BuildDependenciesVersions.TestDependenciesVersions.JUNIT}"
    const val MOCKITO = "org.mockito:mockito-core:${BuildDependenciesVersions.TestDependenciesVersions.MOCKITO}"
    const val MOCKK = "io.mockk:mockk:${BuildDependenciesVersions.TestDependenciesVersions.MOCKK}"

    const val ROBOELECTRIC = "org.robolectric:robolectric:${BuildDependenciesVersions.TestDependenciesVersions.ROBOELECTRIC}"
    const val EXT_JUNIT = "androidx.test.ext:junit:${BuildDependenciesVersions.TestDependenciesVersions.EXT}"
    const val EXT_TRUTH = "androidx.test.ext:truth:${BuildDependenciesVersions.TestDependenciesVersions.EXT}"
    const val TRUTH = "com.google.truth:truth:${BuildDependenciesVersions.TestDependenciesVersions.TRUTH}"


    const val CORE = "androidx.test:core:${BuildDependenciesVersions.TestDependenciesVersions.TEST}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${BuildDependenciesVersions.DependenciesVersions.COROUTINES}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${BuildDependenciesVersions.TestDependenciesVersions.MOCK_WEB_SERVER}"

}