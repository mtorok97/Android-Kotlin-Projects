package hu.bme.aut.android.hfdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.ALLTIPS_PAGE_INDEX
import hu.bme.aut.android.hfdemo.adapter.MYTIPS_PAGE_INDEX
import hu.bme.aut.android.hfdemo.adapter.RANKINGS_PAGE_INDEX
import hu.bme.aut.android.hfdemo.adapter.TipPagerAdapter
import hu.bme.aut.android.hfdemo.databinding.FragmentTipViewPagerBinding

class TipViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTipViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = TipPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MYTIPS_PAGE_INDEX -> getString(R.string.mytips_fragment_title)
            ALLTIPS_PAGE_INDEX -> getString(R.string.alltips_fragment_title)
            RANKINGS_PAGE_INDEX -> getString(R.string.rankings_fragment_title)
            else -> null
        }
    }
}