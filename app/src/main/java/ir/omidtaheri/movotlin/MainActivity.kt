package ir.omidtaheri.movotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.omidtaheri.androidbase.BaseActivity
import ir.omidtaheri.movotlin.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController


        // Setup the bottom navigation view with navController
        bottomNavBar = viewBinding!!.bottomNavView
        bottomNavBar.setupWithNavController(navController)


        // Setup the ActionBar with navController and 4 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.searchFragment,
                R.id.favoriteFragment,
                R.id.favoriteFragment,
                R.id.genreFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


    }


    override fun inflateViewBinding(inflater: LayoutInflater): View? {
        viewBinding = ActivityMainBinding.inflate(inflater)
        return viewBinding!!.root
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }


//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//
//        val movieId = intent?.data?.path?.substring(1)
//
//        movieId?.let {
//
//
//            val navController = fragments.get(0).getNavFragmentController()
//
//            if (navController.currentDestination?.id == R.id.mainFragment) {
//
//                val action =
//                    MainFragmentDirections.actionMainFragmentToDetailFragment(movieId?.toInt()!!)
//                navController.navigate(action)
//
//
//            } else if (navController.currentDestination?.id == R.id.detailFragment) {
//
//                val action =
//                    DetailFragmentDirections.actionDetailFragmentSelf(movieId?.toInt()!!)
//                navController.navigate(action)
//
//
//            } else if (navController.currentDestination?.id == R.id.movieFullListFragment) {
//
//                val action =
//                    MovieFullListFragmentDirections.actionMovieFullListFragmentToDetailFragment(
//                        movieId?.toInt()!!
//                    )
//                navController.navigate(action)
//
//            }
//
//            setItem(0)
//        }
//
//    }


    override fun setUp() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showStringError(message: String) {
    }

    override fun showResError(ResId: Int) {
    }

    override fun showSnackBar(message: String) {
    }

    override fun showToast(message: String) {
    }

    override fun showDialog(message: String) {
    }


}
