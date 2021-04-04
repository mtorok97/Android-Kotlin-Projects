package hu.bme.aut.android.hfdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.FAVOURITES_PAGE_INDEX
import hu.bme.aut.android.hfdemo.adapter.GameonPagerAdapter
import hu.bme.aut.android.hfdemo.adapter.RESULTS_PAGE_INDEX
import hu.bme.aut.android.hfdemo.adapter.TIP_PAGE_INDEX
import hu.bme.aut.android.hfdemo.databinding.FragmentHomeViewPagerBinding


class HomeViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = GameonPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    /*private fun getTabIcon(position: Int): Int {
        return when (position) {
            RESULTS_PAGE_INDEX -> R.drawable.garden_tab_selector
            FAVOURITES_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            TIP_PAGE_INDEX -> R.drawable.garden_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }*/

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            RESULTS_PAGE_INDEX -> getString(R.string.results_fragment_title)
            FAVOURITES_PAGE_INDEX -> getString(R.string.favourites_fragment_title)
            TIP_PAGE_INDEX -> getString(R.string.tip_fragment_title)
            else -> null
        }
    }
}