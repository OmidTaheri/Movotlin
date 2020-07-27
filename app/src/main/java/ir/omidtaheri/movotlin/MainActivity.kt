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
import java.util.*


class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var view_pager: ViewPager2
    lateinit var bottom_nav_bar: BottomNavigationView

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
        mapOf(0 to R.id.MainFragment, 1 to R.id.SearchFragment, 2 to R.id.FavoriteFragment,3 to R.id.GenreFragment)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager = findViewById<ViewPager2>(R.id.pager)

        view_pager.adapter = ViewPagerAdapter(this)
        view_pager.setUserInputEnabled(false)

        // check deeplink only after viewPager is setup
        view_pager.post(this::checkDeepLink)
        view_pager.offscreenPageLimit = fragments.size



        bottom_nav_bar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottom_nav_bar.setOnNavigationItemSelectedListener(this)
        bottom_nav_bar.setOnNavigationItemReselectedListener(this)


        view_pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    val itemId = indexToPage[position] ?: R.id.home
                    if (bottom_nav_bar.selectedItemId != itemId) bottom_nav_bar.selectedItemId =
                        itemId
                }

                override fun onPageScrollStateChanged(state: Int) {}
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
        if (view_pager.currentItem != position) setItem(position)
        return true
    }

    private fun setItem(position: Int) {
        view_pager.currentItem = position
        backStack.push(position)
    }


    override fun onBackPressed() {
        val fragment = fragments[view_pager.currentItem]
        val hadNestedFragments = fragment.onBackPressed()
        // if no fragments were popped
        if (!hadNestedFragments) {
            if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                view_pager.currentItem = backStack.peek()

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
