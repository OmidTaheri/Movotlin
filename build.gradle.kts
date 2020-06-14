import extentions.applyDefault

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildDependenciesVersions.PluginsVersions.KTLINT_PLUGIN
    id("io.gitlab.arturbosch.detekt") version BuildDependenciesVersions.PluginsVersions.DETEKT_PLUGIN
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

//ktlint {
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
//}

//detekt {
//    failFast = true // fail build on any finding
//    buildUponDefaultConfig = true // preconfigure defaults
////    config = files("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
////    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
//
//    reports {
//        html.enabled = true // observe findings in your browser with structure and code snippets
//        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
//        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
//    }
//}
//
//tasks {
//    withType<Detekt> {
//        // Target version of the generated JVM bytecode. It is used for type resolution.
//        this.jvmTarget = "1.8"
//    }
//}