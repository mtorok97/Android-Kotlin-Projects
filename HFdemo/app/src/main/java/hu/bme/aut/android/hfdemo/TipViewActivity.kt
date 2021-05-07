package hu.bme.aut.android.hfdemo

import android.os.Bundle
import hu.bme.aut.android.hfdemo.databinding.ActivityTipViewBinding

class TipViewActivity : BaseActivity() {
    private lateinit var binding: ActivityTipViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}