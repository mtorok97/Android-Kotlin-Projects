package hu.bme.aut.android.footballresults.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.footballresults.fragment.FavouritesFragment
import hu.bme.aut.android.footballresults.fragment.ResultsFragment
import hu.bme.aut.android.footballresults.fragment.TipFragment

const val RESULTS_PAGE_INDEX= 0
const val FAVOURITES_PAGE_INDEX= 1
const val TIP_PAGE_INDEX= 2

class GameonPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            RESULTS_PAGE_INDEX -> ResultsFragment()
            FAVOURITES_PAGE_INDEX -> FavouritesFragment()
            TIP_PAGE_INDEX -> TipFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }
}