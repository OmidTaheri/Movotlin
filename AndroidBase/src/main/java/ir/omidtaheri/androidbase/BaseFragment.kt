package ir.omidtaheri.androidbase

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun setUp()

    abstract fun showLoading()
    abstract fun hideLoading()

    abstract fun showStringError(message: String)
    abstract fun showResError(ResId: Int)


    abstract fun showSnackBar(message: String)
    abstract fun showToast(message: String)


    abstract fun showDialog(message: String)
}