import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies
import dependencies.JetpackDependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.buildConfigStringField
import extentions.implementation
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
            buildConfigStringField("BACKDROP_URL", "http://image.tmdb.org/t/p/w1280")
            buildConfigStringField("POSTER_URL", "http://image.tmdb.org/t/p/w500")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isDebuggable = BuildTypeDebug.debuggable
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
            buildConfigStringField("BACKDROP_URL", "http://image.tmdb.org/t/p/w1280")
            buildConfigStringField("POSTER_URL", "http://image.tmdb.org/t/p/w600")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.LIVEDATA_KTX)
    implementation(UiDependencies.APPCOMPAT)
    implementation(Dependencies.JavaxInject)

    implementation(JetpackDependencies.LIFECYCLE_RUNTIME)
    implementation(JetpackDependencies.LIFECYCLE_VIEWMODEL)
    implementation(JetpackDependencies.LIFECYCLE_VIEWMODEL_SAVED_STATE)

    implementation(UiDependencies.LEANBACK)

    implementation(project(mapOf("path" to BuildModules.DaggerCore)))
    implementation(project(mapOf("path" to BuildModules.AndroidBase)))
    implementation(project(mapOf("path" to BuildModules.Domain)))
    implementation(project(mapOf("path" to BuildModules.UiBase)))

    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.RX_Kotlin)

    implementation(UiDependencies.GLIDE)
    kapt(AnnotationProcessorsDependencies.GLIDE_COMPILER)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)
    addTestsDependencies()
}
