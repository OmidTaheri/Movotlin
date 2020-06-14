package dependencies
object AnnotationProcessorsDependencies {

    const val DAGGER = "com.google.dagger:dagger-compiler:${BuildDependenciesVersions.DependenciesVersions.DAGGER}"

    const val DATABINDING =
        "com.android.databinding:compiler:${BuildDependenciesVersions.DependenciesVersions.DATABINDING}"
    const val ROOM = "androidx.room:room-compiler:${BuildDependenciesVersions.JetpackDependenciesVersions.ROOM}"
    const val AUTO_SERVICE =
        "com.google.auto.service:auto-service:${BuildDependenciesVersions.DependenciesVersions.AUTO_SERVICE}"

    const val GLIDE_COMPILER =
     "com.github.bumptech.glide:compiler:${BuildDependenciesVersions.DependenciesVersions.GLIDE}"


}