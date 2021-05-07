package hu.bme.aut.android.hfdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.hfdemo.databinding.FragmentMytipsBinding

class MytipsFragment : Fragment() {
    private lateinit var binding: FragmentMytipsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMytipsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}