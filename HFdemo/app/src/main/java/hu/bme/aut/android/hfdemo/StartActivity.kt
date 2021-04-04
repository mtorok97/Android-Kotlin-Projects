package hu.bme.aut.android.hfdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.bme.aut.android.hfdemo.databinding.FragmentResultsBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}