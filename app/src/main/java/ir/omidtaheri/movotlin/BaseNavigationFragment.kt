package ir.omidtaheri.advancenavigation

import android.content.Intent
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
        // Inflate the load_state_footer_view_item for this fragment
        return inflater.inflate(_layoutRes, container, false)
    }

    fun onBackPressed(): Boolean {
        return requireActivity()
            .findNavController(_navHostId)
            .navigateUp()
    }

    fun popToRoot() {
        val navController = requireActivity().findNavController(_navHostId)
        navController.popBackStack(navController.graph.startDestination, false)
    }

//    fun handleDeepLink(intent: Intent): Boolean =
//        requireActivity()
//            .findNavController(_navHostId)
//            .handleDeepLink(intent)

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
