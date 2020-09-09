import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.JetpackDependencies
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
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
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
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        create(BuildTypes.RELEASE) {
            keyAlias = getLocalProperty("signing.key.alias")
            keyPassword = getLocalProperty("signing.key.password")
            storeFile = file(getLocalProperty("signing.store.file"))
            storePassword = getLocalProperty("signing.store.password")
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
            buildConfigStringField("BACKDROP_URL", "http://image.tmdb.org/t/p/w1280/")
            buildConfigStringField("POSTER_URL", "http://image.tmdb.org/t/p/w600/")
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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

    implementation(Dependencies.multidex)

    implementation(project(mapOf("path" to BuildModules.Data)))
    implementation(project(mapOf("path" to BuildModules.Domain)))

    implementation(project(mapOf("path" to BuildModules.DaggerCore)))

    implementation(project(mapOf("path" to BuildModules.MainPage)))
    implementation(project(mapOf("path" to BuildModules.Search)))
    implementation(project(mapOf("path" to BuildModules.Favorite)))
    implementation(project(mapOf("path" to BuildModules.GenreList)))
    implementation(project(mapOf("path" to BuildModules.UiBase)))

    implementation(JetpackDependencies.NAVIGATION_FRAGMENT)
    implementation(JetpackDependencies.NAVIGATION_UI)

    implementation(UiDependencies.MATERIAL)
    implementation(UiDependencies.VIEWPAGER2)

    debugImplementation(DebugDependencies.LEAKCANARY)
    implementation(DebugDependencies.STETHO)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    implementation(Dependencies.DAGGER)

    addTestsDependencies()
}
