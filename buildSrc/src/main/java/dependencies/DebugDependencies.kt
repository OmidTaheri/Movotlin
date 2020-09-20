package dependencies
object DebugDependencies {

    const val LEAKCANARY =
        "com.squareup.leakcanary:leakcanary-android:${BuildDependenciesVersions.DependenciesVersions.LEAKCANARY}"

    const val STETHO =
        "com.facebook.stetho:stetho:${BuildDependenciesVersions.DependenciesVersions.STETHO}"

    const val OKHTTP_STETHO =
        "com.facebook.stetho:stetho-okhttp3:${BuildDependenciesVersions.DependenciesVersions.STETHO}"

}