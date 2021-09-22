import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.JetpackDependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.implementation
import extentions.kapt

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    kotlin(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
}

android {
    compileSdkVersion = BuildAndroidConfig.COMPILE_SDK_VERSION.toString()
    buildToolsVersion = BuildAndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION

        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

    flavorDimensions(BuildProductDimensions.BASEDIMENT)

    productFlavors {
        FullFlavor.libraryCreate(this)
        DemoFlavor.libraryCreate(this)
        FullQAFlavor.libraryCreate(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)

    implementation(project(mapOf("path" to BuildModules.Data)))
    implementation(project(mapOf("path" to BuildModules.Domain)))
    implementation(project(mapOf("path" to BuildModules.Local)))
    implementation(project(mapOf("path" to BuildModules.Remote)))

    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_JAVA)

    kapt(AnnotationProcessorsDependencies.ROOM)
    implementation(JetpackDependencies.ROOM)
    implementation(JetpackDependencies.ROOM_KTX)
    implementation(JetpackDependencies.ROOM_RX)

    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_RX)
    implementation(Dependencies.RETROFIT_CONVERTER)

    implementation(DebugDependencies.OKHTTP_STETHO)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)

    addTestsDependencies()
}
