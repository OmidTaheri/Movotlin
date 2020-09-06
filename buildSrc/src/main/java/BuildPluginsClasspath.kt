object BuildPluginsClasspath {

    const val GRADLE_FOR_ANDROID =
        "com.android.tools.build:gradle:${BuildDependenciesVersions.GradlePluginsVersions.GRADLE_ANDROID_PLUGIN}"
    const val GRADLE_FOR_KOTLIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildDependenciesVersions.GradlePluginsVersions.GRADLE_KOTLIN_PLUGIN}"
    const val SAFEARGS =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${BuildDependenciesVersions.GradlePluginsVersions.SAFEARGS}"
}