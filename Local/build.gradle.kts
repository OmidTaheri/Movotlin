import dependencies.Dependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.implementation
import dependencies.AnnotationProcessorsDependencies
import dependencies.JetpackDependencies
import extentions.kapt

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
            isDebuggable = BuildTypeRelease.debuggable
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isDebuggable = BuildTypeDebug.debuggable
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


}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)
    implementation(project(mapOf("path" to BuildModules.Data)))

    kapt(AnnotationProcessorsDependencies.ROOM)
    implementation(JetpackDependencies.ROOM)
    implementation(JetpackDependencies.ROOM_KTX)
    implementation(JetpackDependencies.ROOM_RX)


    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.JavaxInject)

    addTestsDependencies()

}