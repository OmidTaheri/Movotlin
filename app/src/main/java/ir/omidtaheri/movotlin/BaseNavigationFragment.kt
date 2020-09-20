package ir.omidtaheri.advancenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val saveSharedPreferencesActivity: SharedPreferences =
//            requireActivity().getSharedPreferences(
//                "MainActivityState",
//                AppCompatActivity.MODE_PRIVATE
//            )
//
//        var Navcontoller: Bundle? = null
//
//        var NavGraphId: Int = R.navigation.main_nav_graph
//
//        when (_navHostId) {
//
//            R.id.nav_host_main -> {
//                Navcontoller =
//                    loadBundle(saveSharedPreferencesActivity, "NAVIGATION_CONTROLLER_STATE_0")
//                NavGraphId = R.navigation.main_nav_graph
//            }
//
//            R.id.nav_host_search -> {
//                Navcontoller =
//                    loadBundle(saveSharedPreferencesActivity, "NAVIGATION_CONTROLLER_STATE_1")
//                NavGraphId = R.navigation.search_nav_graph
//            }
//            R.id.nav_host_favorite -> {
//                Navcontoller =
//                    loadBundle(saveSharedPreferencesActivity, "NAVIGATION_CONTROLLER_STATE_2")
//                NavGraphId = R.navigation.favorite_nav_graph
//            }
//            R.id.nav_host_genre -> {
//                Navcontoller =
//                    loadBundle(saveSharedPreferencesActivity, "NAVIGATION_CONTROLLER_STATE_3")
//                NavGraphId = R.navigation.genre_nav_graph
//            }
//        }
//
//        Navcontoller?.let {
//            val navfrag = childFragmentManager.findFragmentById(_navHostId) as NavHostFragment
//            navfrag.findNavController().restoreState(it)
//            val graphInflater = navfrag.findNavController().navInflater
//            val navGraph = graphInflater.inflate(NavGraphId)
//            navfrag.findNavController().graph = navGraph
//        }

    }

    fun getNavFragmentController(): NavController {
        val navfrag = childFragmentManager.findFragmentById(_navHostId) as NavHostFragment
        return navfrag.findNavController()
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
