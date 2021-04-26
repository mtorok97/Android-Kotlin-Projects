package hu.bme.aut.android.hfdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.android.hfdemo.databinding.FragmentResultsBinding
import hu.bme.aut.android.hfdemo.fragment.DatePickerDialogFragment
import hu.bme.aut.android.hfdemo.fragment.ResultsFragment
import okhttp3.OkHttpClient
import okhttp3.Request

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}