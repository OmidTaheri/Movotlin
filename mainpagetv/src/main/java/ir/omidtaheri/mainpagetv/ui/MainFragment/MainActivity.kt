package ir.omidtaheri.mainpagetv.ui.MainFragment

import android.app.Activity
import android.os.Bundle
import ir.omidtaheri.androidbase.BaseTvActivity
import ir.omidtaheri.mainpagetv.R

/**
 * Loads [MainFragment].
 */
class MainActivity : BaseTvActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(MainFragment())
    }
}
