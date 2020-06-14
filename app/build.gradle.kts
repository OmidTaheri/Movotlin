import extentions.addTestsDependencies
import dependencies.UiDependencies
import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
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
            buildConfigField("String", "BASE_URL", "\"http://moviesapi.ir/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }


        getByName(BuildTypes.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
            buildConfigField("String", "BASE_URL", "\"http://moviesapi.ir/\"")
        }

    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)
    implementation(UiDependencies.CONSTRAINT_LAYOUT)


    debugImplementation(DebugDependencies.LEAKCANARY)

    kapt(AnnotationProcessorsDependencies.DAGGER)

    addTestsDependencies()
}