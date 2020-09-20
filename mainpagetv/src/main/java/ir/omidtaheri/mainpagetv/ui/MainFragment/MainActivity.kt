package ir.omidtaheri.mainpagetv.ui.MainFragment

import android.os.Bundle
import ir.omidtaheri.androidbase.BaseTvActivity

/**
 * Loads [MainFragmentBrowse].
 */
class MainActivity : BaseTvActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(MainFragmentBrowse())
    }
}
