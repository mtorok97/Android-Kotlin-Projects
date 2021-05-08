package hu.bme.aut.android.hfdemo

import android.os.Bundle
import android.util.Log
import hu.bme.aut.android.hfdemo.databinding.ActivityStartBinding
import java.util.*

class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}