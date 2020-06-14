import extentions.applyDefault


buildscript {

    repositories {
        google()
        jcenter()

    }

    dependencies {
        classpath("${BuildPluginsClasspath.GRADLE_FOR_ANDROID}")
        classpath("${BuildPluginsClasspath.GRADLE_FOR_KOTLIN}")
    }
}

allprojects {
    repositories.applyDefault()
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}