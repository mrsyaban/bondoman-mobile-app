package com.pbd.psi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.psi.databinding.ActivityMainBinding
import com.pbd.psi.databinding.FragmentSettingsBinding
import com.pbd.psi.services.BackgroundService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkChangeReceiver: BroadcastReceiver

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("Broadcast", "Broadcast intent 1")
            if (intent?.action == "com.pbd.psi.ACTION_SEND") {
                Log.d("Broadcast", "Broadcast intent 2")
                val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
                val randomTitleValue = (10..15)
                    .map { kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")

                val randomNominalValue = (1000..2000).random()
                Log.d("Broadcast", "Received broadcast")
                val intent2 = Intent(context, AddTransactionActivity::class.java).apply {
                    putExtra("TITLE", randomTitleValue)
                    putExtra("AMOUNT", randomNominalValue)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Clear all activities above AddTransactionActivity
                }

                startActivity(intent2)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val filter = IntentFilter("com.pbd.psi.ACTION_SEND")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, filter, RECEIVER_EXPORTED)
        }else {
            registerReceiver(broadcastReceiver, filter)
        }
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        if(!isOnline(this)){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Initialize connectivity manager and network change receiver
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Handle network change here, update UI accordingly
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo == null || !networkInfo.isConnected) {
                    // Network lost, handle accordingly
                    // For example, show a toast message
                     showToast("Network connection lost")
                }
            }
        }

        // Register network change receiver
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        // bottom navbar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        val serviceIntent = Intent(this, BackgroundService::class.java)
        startService(serviceIntent)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister network change receiver to avoid memory leaks
        unregisterReceiver(networkChangeReceiver)
        val serviceIntent = Intent(this, BackgroundService::class.java)
        stopService(serviceIntent)
    }

    // Function to show toast message
     private fun showToast(message: String) {
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
     }

    private fun isOnline(context: Context) : Boolean{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        return false
    }
}
