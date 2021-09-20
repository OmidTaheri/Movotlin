package ir.omidtaheri.movotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.omidtaheri.advancenavigation.BaseNavigationFragment
import ir.omidtaheri.androidbase.BaseActivity
import ir.omidtaheri.mainpage.ui.DetailFragment.DetailFragmentDirections
import ir.omidtaheri.mainpage.ui.MainFragment.MainFragmentDirections
import ir.omidtaheri.mainpage.ui.MovieFullList.MovieFullListFragmentDirections
import ir.omidtaheri.movotlin.databinding.ActivityMainBinding
import java.util.*

class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var viewBinding: ActivityMainBinding? = null
    private var savedInstance = false
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavBar: BottomNavigationView

    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    private lateinit var fragments: List<BaseNavigationFragment>

    // map of navigation_id to container index
    private val indexToPage =
        mapOf(
            0 to R.id.MainFragment,
            1 to R.id.SearchFragment,
            2 to R.id.FavoriteFragment,
            3 to R.id.GenreFragment
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstance = false

        fragments = listOf(
            BaseNavigationFragment.newInstance(R.layout.content_main_base, R.id.nav_host_main),
            BaseNavigationFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search),
            BaseNavigationFragment.newInstance(
                R.layout.content_favorite_base,
                R.id.nav_host_favorite
            ),
            BaseNavigationFragment.newInstance(R.layout.content_genre_base, R.id.nav_host_genre)
        )

        if (savedInstanceState != null) {

            fragments.forEachIndexed { index, it ->
                when (index) {
                    0 -> {
                        it.setHost(
                            this@MainActivity,
                            R.layout.content_main_base,
                            R.id.nav_host_main,
                            (application as ApplicationClass).getFragManager0()
                        )

                    }
                    1 -> {
                        it.setHost(
                            this@MainActivity,
                            R.layout.content_search_base,
                            R.id.nav_host_search,
                            (application as ApplicationClass).getFragManager1()
                        )

                    }
                    2 -> {
                        it.setHost(
                            this@MainActivity, R.layout.content_favorite_base,
                            R.id.nav_host_favorite,
                            (application as ApplicationClass).getFragManager2()
                        )

                    }
                    3 -> {
                        it.setHost(
                            this@MainActivity,
                            R.layout.content_genre_base,
                            R.id.nav_host_genre,
                            (application as ApplicationClass).getFragManager3()
                        )
                    }

                }


            }

        }



        viewPager = viewBinding!!.pager
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = fragments.size

        bottomNavBar = viewBinding!!.bottomNavView
        bottomNavBar.setOnNavigationItemSelectedListener(this)
        bottomNavBar.setOnNavigationItemReselectedListener(this)

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    val itemId = indexToPage[position] ?: R.id.home
                    if (bottomNavBar.selectedItemId != itemId) bottomNavBar.selectedItemId = itemId
                }
            }
        )


        // initialize backStack with elements
        if (backStack.empty())
            setItem(0)


        val saveSharedPreferences: SharedPreferences =
            getSharedPreferences("MainActivityState", MODE_PRIVATE)
        val current = saveSharedPreferences.getInt("VIEW_PAGER_POSITION", 0)

        if (current != 0)
            setItem(current)

    }


    override fun inflateViewBinding(inflater: LayoutInflater): View? {
        viewBinding = ActivityMainBinding.inflate(inflater)
        return viewBinding!!.root
    }


    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (viewPager.currentItem != position) setItem(position)
        return true
    }

    private fun setItem(position: Int) {
        viewPager.currentItem = position
        backStack.push(position)
    }


    override fun onBackPressed() {
        val fragment = fragments[viewPager.currentItem]
        val hadNestedFragments = fragment.onBackPressed()
        // if no fragments were popped

        if (!hadNestedFragments) {
            if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                viewPager.currentItem = backStack.peek()
            } else super.onBackPressed()
        }
    }

    inner class ViewPagerAdapter(
        fragmentActivity: FragmentActivity
    ) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val movieId = intent?.data?.path?.substring(1)

        movieId?.let {


            val navController = fragments.get(0).getNavFragmentController()

            if (navController.currentDestination?.id == R.id.mainFragment) {

                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movieId?.toInt()!!)
                navController.navigate(action)


            } else if (navController.currentDestination?.id == R.id.detailFragment) {

                val action =
                    DetailFragmentDirections.actionDetailFragmentSelf(movieId?.toInt()!!)
                navController.navigate(action)


            } else if (navController.currentDestination?.id == R.id.movieFullListFragment) {

                val action =
                    MovieFullListFragmentDirections.actionMovieFullListFragmentToDetailFragment(
                        movieId?.toInt()!!
                    )
                navController.navigate(action)

            }

            setItem(0)
        }

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

    override fun onStop() {
        super.onStop()

        val save: SharedPreferences =
            getSharedPreferences("MainActivityState", MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()

        ed.putInt("VIEW_PAGER_POSITION", viewPager.currentItem)

        ed.commit()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedInstance = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!savedInstance) {
            val save: SharedPreferences =
                getSharedPreferences("MainActivityState", MODE_PRIVATE)
            val ed: SharedPreferences.Editor = save.edit()
            ed.clear().apply()
        }
    }
}
