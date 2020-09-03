package ir.omidtaheri.mainpagetv

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.ErrorSupportFragment

/**
 * This class demonstrates how to extend [androidx.leanback.app.ErrorFragment].
 */
class ErrorFragment : ErrorSupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = resources.getString(R.string.app_name)
    }

    internal fun setErrorContent() {
        imageDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.lb_ic_sad_cloud)
        message = resources.getString(R.string.error_fragment_message)
        setDefaultBackground(TRANSLUCENT)

        buttonText = resources.getString(R.string.dismiss_error)
        buttonClickListener = View.OnClickListener {
            fragmentManager?.beginTransaction()?.remove(this@ErrorFragment)?.commit()
        }
    }

    companion object {
        private const val TRANSLUCENT = true
    }
}
