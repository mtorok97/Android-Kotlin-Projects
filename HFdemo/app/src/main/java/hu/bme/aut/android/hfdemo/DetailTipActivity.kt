package hu.bme.aut.android.hfdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import hu.bme.aut.android.hfdemo.databinding.ActivityDetailTipBinding

class DetailTipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTipBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.getStringArrayExtra("MATCH")
        binding.tvDate.text = args?.get(7)
        Glide.with(this).load(args?.get(5)).into(binding.ivHome)
        Glide.with(this).load(args?.get(6)).into(binding.ivAway)
        binding.tvTeamHome.text = args?.get(1)
        binding.tvTeamAway.text = args?.get(2)
        binding.tvMytip.text = "My tip: ${
            when (args?.get(8)) {
                "Home" -> args?.get(1)
                "Draw" -> "Draw"
                "Away" -> args?.get(2)
                else -> ""
            } 
        }"
        binding.btnHome.setOnClickListener {
            binding.tvMytip.text = "My tip: ${args?.get(1)}"
        }
        binding.btnX.setOnClickListener {
            binding.tvMytip.text = "My tip: Draw"
        }
        binding.btnAway.setOnClickListener {
            binding.tvMytip.text = "My tip: ${args?.get(2)}"
        }
    }
}