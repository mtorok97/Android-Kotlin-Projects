package hu.bme.aut.android.hfdemo

import android.os.Bundle
import hu.bme.aut.android.hfdemo.databinding.ActivityStartBinding

class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}