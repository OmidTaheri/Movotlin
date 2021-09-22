package extentions

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.LibraryBuildType


fun ApplicationBuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}

fun ApplicationBuildType.buildConfigIntField(name: String, value: Int) {
    this.buildConfigField("int", name, value.toString())
}

fun ApplicationBuildType.buildConfigBooleanField(name: String, value: Boolean) {
    this.buildConfigField("boolean", name, value.toString())
}


fun LibraryBuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}

fun LibraryBuildType.buildConfigIntField(name: String, value: Int) {
    this.buildConfigField("int", name, value.toString())
}

fun LibraryBuildType.buildConfigBooleanField(name: String, value: Boolean) {
    this.buildConfigField("boolean", name, value.toString())
}