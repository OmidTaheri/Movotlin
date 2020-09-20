interface BuildTypes {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isCrashlyticsEnabled: Boolean
    val isTestCoverageEnabled: Boolean
    val debuggable: Boolean

}

object BuildTypeDebug : BuildTypes {
    override val isMinifyEnabled = false
    override val isCrashlyticsEnabled = false
    override val isTestCoverageEnabled = true
    override val debuggable = true

    const val applicationIdSuffix = ".debug"
    const val versionNameSuffix = "-DEBUG"


}


object BuildTypeRelease : BuildTypes {
    override val isMinifyEnabled = true
    override val isCrashlyticsEnabled = true
    override val isTestCoverageEnabled = false
    override val debuggable = false
}
