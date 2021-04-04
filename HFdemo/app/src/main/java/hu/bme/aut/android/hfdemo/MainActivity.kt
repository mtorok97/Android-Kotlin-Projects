package hu.bme.aut.android.hfdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.bme.aut.android.hfdemo.databinding.ActivityMainBinding
import hu.bme.aut.android.hfdemo.network.OkHttpHelper
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
        }

        binding.button2.setOnClickListener {
            Thread {
                var data = OkHttpHelper.getRates()
                runOnUiThread {
                    Toast.makeText(this@MainActivity, data, Toast.LENGTH_LONG).show()
                }
            }.start()
        }

    }
}