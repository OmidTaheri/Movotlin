package utils

import org.gradle.api.Project
import java.lang.System.load
import java.util.*


private const val LOCAL_PROPERTIES_FILE_NAME = "local.properties"

fun getLocalProperty(propertyName: String, project: Project): String {

    val localProperties = Properties().apply {
        val localPropertiesFile = project.rootProject.file(LOCAL_PROPERTIES_FILE_NAME)
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }

    }

    return localProperties.getProperty(propertyName)?.let {
        it
    } ?: throw NoSuchFieldException("Not defined property : $propertyName")
}
