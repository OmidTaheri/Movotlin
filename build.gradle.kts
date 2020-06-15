import extentions.applyDefault
import org.jetbrains.dokka.gradle.DokkaTask

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
    }
}

allprojects {
    repositories.applyDefault()
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// ktlint {
//    version.set(BuildDependenciesVersions.PluginsVersions.KTLINT_PLUGIN )
//    debug.set(true)
//    verbose.set(true)
//    android.set(true)
//    outputToConsole.set(true)
//    ignoreFailures.set(true)
//    kotlinScriptAdditionalPaths {
//        include(fileTree("scripts/"))
//    }
//    filter {
//        exclude("**/generated/**")
//        include("**/kotlin/**")
//    }
// }

detekt {
    input = files("src/main/java", "src/main/kotlin")
    debug = false // Adds debug output during task execution. `false` by default.
    ignoreFailures =
        false // If set to `true` the build does not fail when the maxIssues count was reached. Defaults to `false`.
    reports {
        xml {
            enabled = true // Enable/Disable XML report (default: true)
            destination =
                file("build/reports/detekt.xml") // Path where XML report will be stored (default: `build/reports/detekt/detekt.xml`)
        }
        html {
            enabled = true // Enable/Disable HTML report (default: true)
            destination =
                file("build/reports/detekt.html") // Path where HTML report will be stored (default: `build/reports/detekt/detekt.html`)
        }
        txt {
            enabled = true // Enable/Disable TXT report (default: true)
            destination =
                file("build/reports/detekt.txt") // Path where TXT report will be stored (default: `build/reports/detekt/detekt.txt`)
        }
        custom {
            reportId = "CustomJsonReport" // The simple class name of your custom report.
            destination = file("build/reports/detekt.json") // Path where report will be stored
        }
    }



}

tasks {
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
}



