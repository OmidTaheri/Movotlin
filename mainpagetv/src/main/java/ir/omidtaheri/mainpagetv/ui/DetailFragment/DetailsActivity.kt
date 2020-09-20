package ir.omidtaheri.mainpagetv.ui.DetailFragment

import android.os.Bundle
import ir.omidtaheri.androidbase.BaseTvActivity

/**
 * Details activity class that loads [DetailsFragment] class.
 */
class DetailsActivity : BaseTvActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(DetailsFragment())
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "hero"
        const val MOVIEID = "MovieId"
    }
}
