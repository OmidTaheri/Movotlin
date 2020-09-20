import extentions.applyDefault
import org.jetbrains.dokka.gradle.DokkaTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildDependenciesVersions.PluginsVersions.KTLINT_PLUGIN
    id("io.gitlab.arturbosch.detekt") version BuildDependenciesVersions.PluginsVersions.DETEKT_PLUGIN
    id("org.jetbrains.dokka") version BuildDependenciesVersions.PluginsVersions.DOKKA_PLUGIN
}

buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("${BuildPluginsClasspath.GRADLE_FOR_ANDROID}")
        classpath("${BuildPluginsClasspath.GRADLE_FOR_KOTLIN}")
        classpath("${BuildPluginsClasspath.SAFEARGS}")
    }
}

allprojects {
    repositories.applyDefault()
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.JSON)
    }

    filter {
        exclude("**/generated/**", "**/test/**", "**/androidTest/**")
        include("**/kotlin/**")
    }
}

detekt {
    ignoreFailures = true
    config = files("config/detekt/detekt.yml")
}

tasks {
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
}
