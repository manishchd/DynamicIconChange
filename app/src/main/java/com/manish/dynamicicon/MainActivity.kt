package com.manish.dynamicicon

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.manish.dynamicicon.databinding.ActivityMainBinding


open class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val viewModel by lazy { ViewModelProvider(this).get(MainActivityVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickHandler()
    }

    private fun clickHandler() {

        binding.btnWhatsappBlue.setOnClickListener {
            changeToBlue()
        }

        binding.btnWhatsappGreen.setOnClickListener {
            changeToGreen()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("Value","${viewModel.value}")
        Log.e("Value",packageName)
    }

    private fun changeToGreen() {
        try {
            packageManager.setComponentEnabledSetting(
                ComponentName(this@MainActivity, MainActivityBlue::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
            )
            packageManager.setComponentEnabledSetting(
                ComponentName(this@MainActivity, MainActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            viewModel.value += viewModel.value+1
            Toast.makeText(applicationContext, "Launcher new has been applied successfully", Toast.LENGTH_SHORT).show();
            restartAppWithDelay()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun changeToBlue() {
        try {
            packageManager.setComponentEnabledSetting(
                ComponentName(this, MainActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
            )
            packageManager.setComponentEnabledSetting(
                ComponentName(this, MainActivityBlue::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            viewModel.value += viewModel.value+1
            Toast.makeText(applicationContext, "Launcher new has been applied successfully", Toast.LENGTH_SHORT).show();
            restartAppWithDelay()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun restartAppWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({ // Restart the app
            val intent = application.packageManager
                .getLaunchIntentForPackage(application.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, 1000) // Delay of 1 second (adjust as needed)
    }
}