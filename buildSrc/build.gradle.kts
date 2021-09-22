plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}
repositories {
    google()
    jcenter()
}

object PluginsVersions {
    const val GRADLE_ANDROID = "7.0.1"
    const val KOTLIN = "1.5.31"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
}