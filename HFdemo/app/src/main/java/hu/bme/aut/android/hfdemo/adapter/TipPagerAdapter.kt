package hu.bme.aut.android.hfdemo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.hfdemo.fragment.*

const val MYTIPS_PAGE_INDEX= 0
const val ALLTIPS_PAGE_INDEX= 1
const val RANKINGS_PAGE_INDEX= 2

class TipPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            MYTIPS_PAGE_INDEX -> MytipsFragment()
            ALLTIPS_PAGE_INDEX -> AlltipsFragment()
            RANKINGS_PAGE_INDEX -> RankingsFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }
}