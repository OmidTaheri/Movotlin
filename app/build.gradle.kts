import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.kapt

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    kotlin(BuildPlugins.KOTLIN_ANDROID)
    kotlin(BuildPlugins.KOTLIN_ANDROID_EXTENSIONS)
    id(BuildPlugins.KOTLIN_KAPT)
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            buildConfigField("String", "BASE_URL", "\"hhttps://api.themoviedb.org/3/\"")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lintOptions {
        setCheckDependencies(true)
        setHtmlReport(true)
        setHtmlOutput(file("${project.rootDir}/build/reports/lint/lint_report_${BuildAndroidConfig.VERSION_NAME}.html"))
    }

}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)
    implementation(UiDependencies.CONSTRAINT_LAYOUT)
<<<<<<< HEAD
<<<<<<< HEAD
    implementation(project(mapOf("path" to ":Data")))
=======
    implementation(project(mapOf("path" to ":Domain")))
=======

<<<<<<< HEAD
    implementation(project(mapOf("path" to ":Data")))
>>>>>>> b99cc7c... config local module
=======
    implementation(project(mapOf("path" to ":DaggerCore")))

>>>>>>> ef04c8d... add DaggerCore module and add application class by dagger dependency

>>>>>>> db514c9... add interactor/base package to domain module and convert domain.gradle to kotlin dsl
    debugImplementation(DebugDependencies.LEAKCANARY)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)

   addTestsDependencies()
}
