package ir.omidtaheri.searchtv

import android.app.Activity
import android.os.Bundle

/**
 * Loads [MainFragment].
 */
class SearchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
