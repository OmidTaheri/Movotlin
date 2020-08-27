package ir.omidtaheri.remote.testUtils

import com.google.common.io.Resources.getResource
import java.io.File

internal fun getJson(path: String): String {
    val uri = getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}
