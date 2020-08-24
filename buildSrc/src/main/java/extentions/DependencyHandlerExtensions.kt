package extentions

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.Dependency
import dependencies.TestAndroidDependencies
import dependencies.TestDependencies

fun DependencyHandler.debugImplementation(dependencyNotation: String): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: String): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: String): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: String): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.addTestsDependencies() {


    testImplementation(TestDependencies.MOCKK)
    testImplementation(TestDependencies.CORE)
    testImplementation(TestDependencies.JUNIT)
    testImplementation(TestDependencies.MOCKITO)
    testImplementation(TestDependencies.COROUTINES_TEST)
    testImplementation(TestDependencies.MOCK_WEB_SERVER)
    testImplementation(TestDependencies.ROBOELECTRIC)
    testImplementation(TestDependencies.EXT_JUNIT)
    testImplementation(TestDependencies.EXT_TRUTH)
    testImplementation(TestDependencies.TRUTH)




    androidTestImplementation(TestDependencies.CORE)
    androidTestImplementation(TestDependencies.EXT_JUNIT)
    androidTestImplementation(TestDependencies.EXT_TRUTH)
    androidTestImplementation(TestDependencies.TRUTH)

    androidTestImplementation(TestAndroidDependencies.MOCKK)
    androidTestImplementation(TestAndroidDependencies.RUNNER)
    androidTestImplementation(TestAndroidDependencies.RULES)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CORE)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CONTRIB)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_ACCESSIBILITY)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CONCURRENT)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_INTENTS)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_RESOURCE)
    androidTestImplementation(TestAndroidDependencies.HAMCREST)
    androidTestImplementation(TestAndroidDependencies.UIAUTOMATOR)


}