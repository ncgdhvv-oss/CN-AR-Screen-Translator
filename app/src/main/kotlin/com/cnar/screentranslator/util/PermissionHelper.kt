package com.cnar.screentranslator.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import timber.log.Timber

object PermissionHelper {
    fun hasInternetPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun hasForegroundServicePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.FOREGROUND_SERVICE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun canDrawOverlays(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun openOverlaySettings(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            android.net.Uri.parse("package:${context.packageName}")
        )
        context.startActivity(intent)
        Timber.d("Opened overlay settings")
    }

    fun getRequiredPermissions(): Array<String> {
        val permissions = mutableListOf<String>(
            Manifest.permission.INTERNET,
            Manifest.permission.WAKE_LOCK
        )
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE)
        }
        
        return permissions.toTypedArray()
    }
}
