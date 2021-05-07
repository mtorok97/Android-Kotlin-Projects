package hu.bme.aut.android.hfdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.hfdemo.databinding.FragmentAlltipsBinding

class AlltipsFragment : Fragment() {
    private lateinit var binding: FragmentAlltipsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlltipsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}
