package ir.omidtaheri.movotlin

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
import ir.omidtaheri.movotlin.databinding.ActivityMainBinding


class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var viewBinding: ActivityMainBinding? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavBar: BottomNavigationView
    private var currentPage = 0

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

        if (savedInstanceState != null) {

            currentPage = savedInstanceState.getInt("VIEW_PAGER_POSITION", 0)
            val stackIntArray = savedInstanceState.getIntArray("stack")
            stackIntArray?.let {
                for (item in stackIntArray.indices.reversed()) {
                    backStack.push(stackIntArray[item])
                }
            }

            val KEY_PREFIX_FRAGMENT = "Movotlin"
            val keyFrag1 =
                createKey(KEY_PREFIX_FRAGMENT, 0)
            val keyFrag2 =
                createKey(KEY_PREFIX_FRAGMENT, 1)
            val keyFrag3 =
                createKey(KEY_PREFIX_FRAGMENT, 2)
            val keyFrag4 =
                createKey(KEY_PREFIX_FRAGMENT, 3)

            val fragment1 = supportFragmentManager.getFragment(savedInstanceState, keyFrag1)
            val fragment2 = supportFragmentManager.getFragment(savedInstanceState, keyFrag2)
            val fragment3 = supportFragmentManager.getFragment(savedInstanceState, keyFrag3)
            val fragment4 = supportFragmentManager.getFragment(savedInstanceState, keyFrag4)

            fragments = listOf(
                fragment1 as BaseNavigationFragment,
                fragment2 as BaseNavigationFragment,
                fragment3 as BaseNavigationFragment,
                fragment4 as BaseNavigationFragment
            )


        } else {
            fragments = listOf(
                BaseNavigationFragment.newInstance(R.layout.content_main_base, R.id.nav_host_main),
                BaseNavigationFragment.newInstance(
                    R.layout.content_search_base,
                    R.id.nav_host_search
                ),
                BaseNavigationFragment.newInstance(
                    R.layout.content_favorite_base,
                    R.id.nav_host_favorite
                ),
                BaseNavigationFragment.newInstance(R.layout.content_genre_base, R.id.nav_host_genre)
            )
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

        if (backStack.isEmpty())
            setItem(currentPage)
        else {
            backStack.pop()
            setItem(currentPage)
        }

    }

    private fun createKey(keyPrefixFragment: String, itemId: Long): String {
        return keyPrefixFragment + itemId
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
                viewPager.currentItem = backStack.peek()!!
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("VIEW_PAGER_POSITION", viewPager.currentItem)

        val stackArrayList = mutableListOf<Int>()
        while (!backStack.isEmpty()) {
            stackArrayList.add(backStack.pop()!!)
        }
        outState.putIntArray("stack", stackArrayList.toIntArray())

        val KEY_PREFIX_FRAGMENT = "Movotlin"
        val keyFrag1 =
            createKey(KEY_PREFIX_FRAGMENT, 0)
        val keyFrag2 =
            createKey(KEY_PREFIX_FRAGMENT, 1)
        val keyFrag3 =
            createKey(KEY_PREFIX_FRAGMENT, 2)
        val keyFrag4 =
            createKey(KEY_PREFIX_FRAGMENT, 3)

        supportFragmentManager.putFragment(outState, keyFrag1, fragments[0])
        supportFragmentManager.putFragment(outState, keyFrag2, fragments[1])
        supportFragmentManager.putFragment(outState, keyFrag3, fragments[2])
        supportFragmentManager.putFragment(outState, keyFrag4, fragments[3])

    }


}
