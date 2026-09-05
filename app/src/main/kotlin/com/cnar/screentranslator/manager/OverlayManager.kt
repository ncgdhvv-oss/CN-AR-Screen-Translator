package com.cnar.screentranslator.manager

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import timber.log.Timber

class OverlayManager(private val context: Context) {
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "overlay_prefs",
        Context.MODE_PRIVATE
    )
    private val overlayViews = mutableMapOf<String, TextView>()

    fun showTranslation(id: String, translation: String, boundingBox: Rect) {
        try {
            if (!Settings.canDrawOverlays(context)) {
                Timber.w("Permission to draw overlay not granted")
                return
            }

            // Remove existing overlay if present
            if (overlayViews.containsKey(id)) {
                removeOverlay(id)
            }

            val textView = createOverlayView(translation)
            val layoutParams = createLayoutParams(boundingBox)

            windowManager.addView(textView, layoutParams)
            overlayViews[id] = textView

            Timber.d("Overlay shown: $id - $translation")
        } catch (e: Exception) {
            Timber.e(e, "Error showing overlay")
        }
    }

    private fun createOverlayView(text: String): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = preferences.getInt("font_size", 16).toFloat()
            setTextColor(preferences.getInt("text_color", Color.WHITE))
            
            val bgColor = preferences.getInt("bg_color", Color.BLACK)
            val opacity = preferences.getInt("bg_opacity", 80)
            val bgColorWithAlpha = Color.argb(
                (255 * opacity / 100).toInt(),
                Color.red(bgColor),
                Color.green(bgColor),
                Color.blue(bgColor)
            )
            setBackgroundColor(bgColorWithAlpha)
            
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setMaxLines(3)
            ellipsize = android.text.TextUtils.TruncateAt.END
        }
    }

    private fun createLayoutParams(boundingBox: Rect): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            
            width = (boundingBox.width() * 1.2).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
            
            x = boundingBox.left
            y = boundingBox.bottom + 10
            
            gravity = Gravity.TOP or Gravity.LEFT
        }
        return params
    }

    fun removeOverlay(id: String) {
        try {
            overlayViews[id]?.let {
                windowManager.removeView(it)
                overlayViews.remove(id)
                Timber.d("Overlay removed: $id")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error removing overlay")
        }
    }

    fun removeAllOverlays() {
        val ids = overlayViews.keys.toList()
        ids.forEach { removeOverlay(it) }
        Timber.d("All overlays removed")
    }

    fun updateFontSize(size: Int) {
        preferences.edit().putInt("font_size", size).apply()
        overlayViews.values.forEach { it.textSize = size.toFloat() }
    }

    fun updateOpacity(opacity: Int) {
        preferences.edit().putInt("bg_opacity", opacity).apply()
    }

    fun updateTextColor(color: Int) {
        preferences.edit().putInt("text_color", color).apply()
        overlayViews.values.forEach { it.setTextColor(color) }
    }

    fun updateBgColor(color: Int) {
        preferences.edit().putInt("bg_color", color).apply()
    }
}
