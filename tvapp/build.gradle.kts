import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.UiDependencies
import extentions.addTestsDependencies
import extentions.buildConfigStringField
import extentions.getLocalProperty
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
        applicationId = BuildAndroidConfig.TV_APPLICATION_ID
        minSdkVersion(BuildAndroidConfig.TV_MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        create(BuildTypes.RELEASE) {
            keyAlias = getLocalProperty("signing.tvkey.alias")
            keyPassword = getLocalProperty("signing.tvkey.password")
            storeFile = file(getLocalProperty("signing.tvstore.file"))
            storePassword = getLocalProperty("signing.tvstore.password")
        }
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isDebuggable = BuildTypeRelease.debuggable
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
            isShrinkResources = BuildTypeRelease.isMinifyEnabled
            buildConfigStringField("BASE_URL", "https://api.themoviedb.org/3/")
            buildConfigStringField("API_KEY", getLocalProperty("API.KEY"))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(name)
        }

        getByName(BuildTypes.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isDebuggable = BuildTypeDebug.debuggable
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
            isShrinkResources = BuildTypeDebug.isMinifyEnabled
            buildConfigStringField("BASE_URL", "https://api.themoviedb.org/3/")
            buildConfigStringField("API_KEY", getLocalProperty("API.KEY"))
        }
    }

    flavorDimensions(BuildProductDimensions.BASEDIMENT)

    productFlavors {
        FullFlavor.appCreate(this)
        DemoFlavor.appCreate(this)
        FullQAFlavor.appCreate(this)
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(UiDependencies.APPCOMPAT)
    implementation(UiDependencies.CONSTRAINT_LAYOUT)

    implementation(Dependencies.multidex)

    implementation(project(mapOf("path" to BuildModules.Data)))
    implementation(project(mapOf("path" to BuildModules.Domain)))
    implementation(project(mapOf("path" to BuildModules.DaggerCore)))
    implementation(project(mapOf("path" to BuildModules.mainpagetv)))

    debugImplementation(DebugDependencies.LEAKCANARY)
    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)

    addTestsDependencies()
}
