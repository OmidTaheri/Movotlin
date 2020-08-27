package ir.omidtaheri.movotlin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.omidtaheri.advancenavigation.BaseNavigationFragment
import java.util.Stack

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var viewPager: ViewPager2
    lateinit var bottomNavBar: BottomNavigationView

    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    private val fragments = listOf(
        BaseNavigationFragment.newInstance(R.layout.content_main_base, R.id.nav_host_main),
        BaseNavigationFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search),
        BaseNavigationFragment.newInstance(R.layout.content_favorite_base, R.id.nav_host_favorite),
        BaseNavigationFragment.newInstance(R.layout.content_genre_base, R.id.nav_host_genre)
    )

    // map of navigation_id to container index
    private val indexToPage =
        mapOf(0 to R.id.MainFragment, 1 to R.id.SearchFragment, 2 to R.id.FavoriteFragment, 3 to R.id.GenreFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById<ViewPager2>(R.id.pager)

        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.setUserInputEnabled(false)

        // check deeplink only after viewPager is setup
        viewPager.post(this::checkDeepLink)
        viewPager.offscreenPageLimit = fragments.size

        bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
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
        if (backStack.empty()) backStack.push(0)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position!!]
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

    private fun checkDeepLink() {
        fragments.forEachIndexed { index, fragment ->
            val hasDeepLink = fragment.handleDeepLink(intent)
            if (hasDeepLink) setItem(index)
        }
    }

    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}
