import dependencies.AnnotationProcessorsDependencies
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
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
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

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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

    implementation(project(mapOf("path" to BuildModules.DaggerCore)))
    implementation(project(mapOf("path" to BuildModules.AndroidBase)))
    implementation(project(mapOf("path" to BuildModules.Domain)))
    implementation(project(mapOf("path" to BuildModules.UiBase)))
    implementation(project(mapOf("path" to BuildModules.ViewComponents)))

    implementation(JetpackDependencies.LIFECYCLE_EXTENSIONS)
    implementation(JetpackDependencies.LIFECYCLE_VIEWMODEL)
    implementation(JetpackDependencies.NAVIGATION_FRAGMENT)
    implementation(JetpackDependencies.NAVIGATION_UI)

    implementation(UiDependencies.CONSTRAINT_LAYOUT)
    implementation(UiDependencies.RECYCLE_VIEW)
    implementation(UiDependencies.MATERIAL)
    implementation(UiDependencies.SWIPE_REFRESH_LAYOUT)

    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.RX_Kotlin)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)
    addTestsDependencies()
}
