package dependencies

import BuildDependenciesVersions

object Dependencies {

    const val KOTLIN =
        "org.jetbrains.kotlin:kotlin-stdlib:${BuildDependenciesVersions.DependenciesVersions.KOTLIN}"

    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.DependenciesVersions.COROUTINES}"

    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.DependenciesVersions.COROUTINES}"


    const val CORE_KTX =
        "androidx.core:core-ktx:${BuildDependenciesVersions.DependenciesVersions.CORE_KTX}"

    const val FRAGMENT_KTX =
        "androidx.fragment:fragment-ktx:${BuildDependenciesVersions.DependenciesVersions.FRAGMENT_KTX}"

    const val LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${BuildDependenciesVersions.DependenciesVersions.LIVEDATA_KTX}"


    const val DAGGER =
        "com.google.dagger:dagger:${BuildDependenciesVersions.DependenciesVersions.DAGGER}"


    const val RETROFIT =
        "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.DependenciesVersions.RETROFIT}"

    const val RETROFIT_RX =
        "com.squareup.retrofit2:adapter-rxjava2:${BuildDependenciesVersions.DependenciesVersions.RETROFIT}"


    const val RETROFIT_CONVERTER =
        "com.squareup.retrofit2:converter-gson:${BuildDependenciesVersions.DependenciesVersions.RETROFIT}"

    const val PLAY_CORE =
        "com.google.android.play:core:${BuildDependenciesVersions.DependenciesVersions.PLAY_CORE}"

    const val RX_ANDROID =
        "io.reactivex.rxjava2:rxandroid:${BuildDependenciesVersions.DependenciesVersions.RXANDROID}"

    const val RX_JAVA =
        "io.reactivex.rxjava2:rxjava:${BuildDependenciesVersions.DependenciesVersions.RXJAVA}"


    const val RX_Kotlin =
        "io.reactivex.rxjava2:rxkotlin:${BuildDependenciesVersions.DependenciesVersions.RXKotlin}"


    const val Annotation =
        "androidx.annotation:annotation:${BuildDependenciesVersions.DependenciesVersions.ANOTATION}"


    const val JavaxInject =
        "javax.inject:javax.inject:${BuildDependenciesVersions.DependenciesVersions.JAVAX_INJECT}"

    const val javaxAnnotation =
        "javax.annotation:jsr250-api:${BuildDependenciesVersions.DependenciesVersions.JAVAX_ANNOTATION}"


    const val multidex =
        "androidx.multidex:multidex:${BuildDependenciesVersions.DependenciesVersions.MULTIDEX}"

}