import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.implementation
import extentions.kapt
import dependencies.JetpackDependencies

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    kotlin(BuildPlugins.KOTLIN_ANDROID)
    kotlin(BuildPlugins.KOTLIN_ANDROID_EXTENSIONS)
    id(BuildPlugins.KOTLIN_KAPT)
}


android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)

    implementation(project(mapOf("path" to ":Data")))
    implementation(project(mapOf("path" to ":Domain")))
    implementation(project(mapOf("path" to ":Local")))
    implementation(project(mapOf("path" to ":Remote")))

    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_JAVA)

    implementation(AnnotationProcessorsDependencies.ROOM)
    implementation(JetpackDependencies.ROOM)
    implementation(JetpackDependencies.ROOM_KTX)
    implementation(JetpackDependencies.ROOM_RX)


    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_RX)
    implementation(Dependencies.RETROFIT_CONVERTER)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)
    addTestsDependencies()
}