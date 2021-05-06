package hu.bme.aut.android.footballresults.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.footballresults.R
import hu.bme.aut.android.footballresults.adapter.FAVOURITES_PAGE_INDEX
import hu.bme.aut.android.footballresults.adapter.GameonPagerAdapter
import hu.bme.aut.android.footballresults.adapter.RESULTS_PAGE_INDEX
import hu.bme.aut.android.footballresults.adapter.TIP_PAGE_INDEX
import hu.bme.aut.android.footballresults.databinding.FragmentHomeViewPagerBinding


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

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            RESULTS_PAGE_INDEX -> getString(R.string.results_fragment_title)
            FAVOURITES_PAGE_INDEX -> getString(R.string.favourites_fragment_title)
            TIP_PAGE_INDEX -> getString(R.string.tip_fragment_title)
            else -> null
        }
    }
}