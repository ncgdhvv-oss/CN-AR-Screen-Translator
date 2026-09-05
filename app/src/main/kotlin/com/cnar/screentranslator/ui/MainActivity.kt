package com.cnar.screentranslator.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cnar.screentranslator.R
import com.cnar.screentranslator.databinding.ActivityMainBinding
import com.cnar.screentranslator.service.TranslationService
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isTranslating = false

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        private const val SCREEN_CAPTURE_REQUEST_CODE = 101
        private const val OVERLAY_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupClickListeners()
        checkPermissions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupClickListeners() {
        binding.btnStart.setOnClickListener {
            startTranslation()
        }

        binding.btnStop.setOnClickListener {
            stopTranslation()
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun checkPermissions() {
        val requiredPermissions = mutableListOf<String>()

        // Check INTERNET permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(Manifest.permission.INTERNET)
        }

        // Check FOREGROUND_SERVICE permission (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.FOREGROUND_SERVICE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requiredPermissions.add(Manifest.permission.FOREGROUND_SERVICE)
            }
        }

        // Check SYSTEM_ALERT_WINDOW permission
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                android.net.Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
        }

        if (requiredPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                requiredPermissions.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startTranslation() {
        Timber.d("Starting translation service")
        isTranslating = true
        binding.btnStart.isEnabled = false
        binding.btnStop.isEnabled = true
        binding.statusMessage.text = getString(R.string.msg_translating)

        val intent = Intent(this, TranslationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopTranslation() {
        Timber.d("Stopping translation service")
        isTranslating = false
        binding.btnStart.isEnabled = true
        binding.btnStop.isEnabled = false
        binding.statusMessage.text = getString(R.string.msg_ready)

        val intent = Intent(this, TranslationService::class.java)
        stopService(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("Permissions granted")
                } else {
                    Timber.e("Permissions denied")
                    binding.statusMessage.text = getString(R.string.error_permission_denied)
                }
            }
        }
    }

    override fun onDestroy() {
        if (isTranslating) {
            stopTranslation()
        }
        super.onDestroy()
    }
}
