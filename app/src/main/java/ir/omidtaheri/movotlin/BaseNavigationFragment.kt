package ir.omidtaheri.advancenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class BaseNavigationFragment : Fragment() {

    private var _layoutRes = -1
    private var _navHostId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _layoutRes = it.getInt("LayoutRes")
            _navHostId = it.getInt("NavHostId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(_layoutRes, container, false)
    }


    fun onBackPressed(): Boolean {

        return if (isAdded) {
            requireActivity()
                .findNavController(_navHostId)
                .navigateUp()
        } else {
            false
        }
    }

    fun popToRoot() {

        if (isAdded) {
            val navController = requireActivity().findNavController(_navHostId)
            navController.popBackStack(navController.graph.startDestination, false)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(layoutres: Int, navhostid: Int) =
            BaseNavigationFragment().apply {
                arguments = Bundle().apply {
                    putInt("LayoutRes", layoutres)
                    putInt("NavHostId", navhostid)
                }
            }
    }

}
