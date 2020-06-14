import extentions.applyDefault

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildDependenciesVersions.PluginsVersions.KTLINT_PLUGIN
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
