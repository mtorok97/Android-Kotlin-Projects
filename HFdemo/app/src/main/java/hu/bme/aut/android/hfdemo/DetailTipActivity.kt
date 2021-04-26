package hu.bme.aut.android.hfdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.hfdemo.databinding.ActivityDetailTipBinding

class DetailTipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.getStringArrayExtra("MATCH")
        binding.tvdata3.text= "${args?.get(0)} ${args?.get(1)} ${args?.get(2)} ${args?.get(3)} ${args?.get(
            4
        )}"
    }
}