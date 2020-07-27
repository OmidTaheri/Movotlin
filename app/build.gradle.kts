import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.JetpackDependencies
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
            buildConfigField("String", "API_KEY", "f5a42625ecb8794bcb6ae2d7238bea7e")
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
            buildConfigField("String", "API_KEY", "f5a42625ecb8794bcb6ae2d7238bea7e")
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

    implementation(project(mapOf("path" to ":Data")))

    implementation(project(mapOf("path" to ":Domain")))



    implementation(project(mapOf("path" to ":Data")))

    implementation(project(mapOf("path" to ":DaggerCore")))

    implementation(project(mapOf("path" to ":MainPage")))
    implementation(project(mapOf("path" to ":Search")))
    implementation(project(mapOf("path" to ":Favorite")))
    implementation(project(mapOf("path" to ":GenreList")))


    implementation( JetpackDependencies.NAVIGATION_FRAGMENT)
    implementation( JetpackDependencies.NAVIGATION_UI)

    implementation(UiDependencies.MATERIAL)
    implementation(UiDependencies.VIEWPAGER2)


    debugImplementation(DebugDependencies.LEAKCANARY)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)

   addTestsDependencies()
}
