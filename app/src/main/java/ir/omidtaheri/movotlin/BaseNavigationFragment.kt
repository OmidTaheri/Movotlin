package ir.omidtaheri.advancenavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController



class BaseNavigationFragment : Fragment() {

    private var LayoutRes = -1
    private var NavHostId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            LayoutRes = it.getInt("LayoutRes")
            NavHostId = it.getInt("NavHostId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the load_state_footer_view_item for this fragment
        return inflater.inflate(LayoutRes, container, false)
    }

    fun onBackPressed(): Boolean {
        return requireActivity()
            .findNavController(NavHostId)
            .navigateUp()
    }

    fun popToRoot() {
        val navController = requireActivity().findNavController(NavHostId)
        navController.popBackStack(navController.graph.startDestination, false)
    }

    fun handleDeepLink(intent: Intent): Boolean =
        requireActivity()
            .findNavController(NavHostId)
            .handleDeepLink(intent)

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