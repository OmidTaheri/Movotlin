import extentions.applyDefault
import org.jetbrains.dokka.gradle.DokkaTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildDependenciesVersions.PluginsVersions.KTLINT_PLUGIN
    id("io.gitlab.arturbosch.detekt") version BuildDependenciesVersions.PluginsVersions.DETEKT_PLUGIN
    id("org.jetbrains.dokka") version BuildDependenciesVersions.PluginsVersions.DOKKA_PLUGIN
}

buildscript {

    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("${BuildPluginsClasspath.GRADLE_FOR_ANDROID}")
        classpath("${BuildPluginsClasspath.GRADLE_FOR_KOTLIN}")
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
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
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.JSON)
    }
}

detekt {
    debug = true // Adds debug output during task execution. `false` by default.
    ignoreFailures =
        true // If set to `true` the build does not fail when the maxIssues count was reached. Defaults to `false`.
    reports {

        html {
            enabled = true // Enable/Disable HTML report (default: true)
            destination =
                file("${project.rootDir}/build/reports/detekt/detekt_${BuildAndroidConfig.VERSION_NAME}.html") // Path where HTML report will be stored (default: `build/reports/detekt/detekt.html`)
        }

    }


}

tasks {
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
}



