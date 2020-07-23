import dependencies.Dependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.implementation
import dependencies.JetpackDependencies
import extentions.kapt
import dependencies.AnnotationProcessorsDependencies

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
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            buildConfigField("String", "BASE_URL", "\"hhttps://api.themoviedb.org/3/\"")
        }
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

}



dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)

    implementation(UiDependencies.CONSTRAINT_LAYOUT)
    implementation(UiDependencies.RECYCLE_VIEW)


    addTestsDependencies()

}