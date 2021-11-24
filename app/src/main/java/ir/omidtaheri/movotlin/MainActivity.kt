package ir.omidtaheri.movotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.omidtaheri.androidbase.BaseActivity
import ir.omidtaheri.movotlin.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    //back stack of root destinations
    private val backStack = Stack<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController


        // Setup the bottom navigation view with navController
        bottomNavBar = viewBinding!!.bottomNavView
        bottomNavBar.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (isRootDestination(destination)) {
                setItem(destination.id)
            }
        }

        bottomNavBar.setOnNavigationItemReselectedListener {
            navController.popBackStack(it.itemId, false)
        }
    }

    private fun isRootDestination(destination: NavDestination?): Boolean {
        return destination?.id == R.id.mainFragment ||
                destination?.id == R.id.searchFragment ||
                destination?.id == R.id.favoriteFragment ||
                destination?.id == R.id.genreFragment
    }

    override fun onBackPressed() {

        if (!isRootDestination(navController.currentDestination))
            navController.navigateUp()
        else {
            if (backStack.size > 1) {
                backStack.pop()
                if (bottomNavBar.selectedItemId != backStack.peek())
                    bottomNavBar.selectedItemId = backStack.peek()!!
            } else super.onBackPressed()
        }

    }

    private fun setItem(menuId: Int) {
        if (!backStack.isEmpty()) {
            if (backStack.peek() != menuId)
                backStack.push(menuId)
        } else backStack.push(menuId)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): View? {
        viewBinding = ActivityMainBinding.inflate(inflater)
        return viewBinding!!.root
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }


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
