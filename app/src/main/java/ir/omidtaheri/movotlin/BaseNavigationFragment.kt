package ir.omidtaheri.advancenavigation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ir.omidtaheri.movotlin.ApplicationClass
import ir.omidtaheri.movotlin.R


class BaseNavigationFragment : Fragment() {

    private var _layoutRes = -1
    private var _navHostId = -1
    private lateinit var hostActivity: Activity
    private lateinit var myChildFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _layoutRes = it.getInt("LayoutRes")
            _navHostId = it.getInt("NavHostId")
        }

        when (_layoutRes) {

            R.layout.content_main_base -> {
                (requireActivity().application as ApplicationClass).setFragManager0(
                    childFragmentManager
                )
            }
            R.layout.content_search_base -> {
                (requireActivity().application as ApplicationClass).setFragManager1(
                    childFragmentManager
                )
            }
            R.layout.content_favorite_base -> {
                (requireActivity().application as ApplicationClass).setFragManager2(
                    childFragmentManager
                )
            }
            R.layout.content_genre_base -> {
                (requireActivity().application as ApplicationClass).setFragManager3(
                    childFragmentManager
                )
            }

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
        if (isAdded) {
            return requireActivity()
                .findNavController(_navHostId)
                .navigateUp()
        } else {

            when (::hostActivity.isInitialized) {
                true ->
                    return hostActivity
                        .findNavController(_navHostId)
                        .navigateUp()

                false -> return false
            }

        }
    }

    fun popToRoot() {

        if (isAdded) {
            val navController = requireActivity().findNavController(_navHostId)
            navController.popBackStack(navController.graph.startDestination, false)
        } else {
            when (::hostActivity.isInitialized) {
                true -> {
                    val navController = hostActivity.findNavController(_navHostId)
                    navController.popBackStack(navController.graph.startDestination, false)
                }
            }
        }

    }


    fun getNavFragmentController(): NavController {

        if (isAdded) {

            val navfrag = childFragmentManager.findFragmentById(_navHostId) as NavHostFragment
            return navfrag.findNavController()

        } else {
            when (::myChildFragmentManager.isInitialized) {
                true -> {
                    val navfrag =
                        myChildFragmentManager.findFragmentById(_navHostId) as NavHostFragment
                    return navfrag.findNavController()
                }

                false -> {
                    throw  Exception("not found childFragmentManager")
                }

            }
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

    fun setHost(
        activity: Activity,
        layoutRes: Int,
        navHostId: Int,
        fragmentManager: FragmentManager
    ) {
        hostActivity = activity
        _layoutRes = layoutRes
        _navHostId = navHostId
        myChildFragmentManager = fragmentManager
    }
}
