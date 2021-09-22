import BuildTypeDebug.versionNameSuffix
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryProductFlavor
import org.gradle.api.NamedDomainObjectContainer

interface BuildProductFlavor {
    val name: String

    fun libraryCreate(
        namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>
    ): LibraryProductFlavor

    fun appCreate(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>
    ): ApplicationProductFlavor
}

object FullFlavor : BuildProductFlavor {

    override val name = "full"

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {

        return namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-full"
            dimension = BuildProductDimensions.BASEDIMENT
        }

    }

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {

        return namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".full"
            versionNameSuffix = "-full"
            dimension = BuildProductDimensions.BASEDIMENT
        }
    }

}


object DemoFlavor : BuildProductFlavor {

    override val name = "demo"

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {

        return namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-demo"
            dimension = BuildProductDimensions.BASEDIMENT
        }

    }

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {

        return namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
            dimension = BuildProductDimensions.BASEDIMENT
        }
    }

}


object FullQAFlavor : BuildProductFlavor {

    override val name = "QA"

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {

        return namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-QA"
            dimension = BuildProductDimensions.BASEDIMENT
        }

    }

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {

        return namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".QA"
            versionNameSuffix = "-QA"
            dimension = BuildProductDimensions.BASEDIMENT
        }
    }

}
