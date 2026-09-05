package com.cnar.screentranslator.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.os.Handler
import android.os.Looper
import android.view.Display
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ScreenCaptureManager(
    private val context: Context,
    private val mediaProjection: MediaProjection
) {
    private var imageReader: ImageReader? = null
    private var display: Display? = null
    private val handler = Handler(Looper.getMainLooper())

    fun startCapture(): Boolean {
        return try {
            val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            display = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
            
            val width = display?.width ?: return false
            val height = display?.height ?: return false
            val density = display?.refreshRate?.toInt() ?: 1

            imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)
            val surface = imageReader!!.surface
            
            mediaProjection.createVirtualDisplay(
                "screen_capture",
                width,
                height,
                density,
                0,
                surface,
                null,
                handler
            )
            
            Timber.d("Screen capture started: ${width}x${height}")
            true
        } catch (e: Exception) {
            Timber.e(e, "Error starting screen capture")
            false
        }
    }

    suspend fun captureFrame(): Bitmap? = withContext(Dispatchers.Default) {
        return@withContext try {
            val image = imageReader?.acquireLatestImage() ?: return@withContext null
            
            val bitmap = imageToBitmap(image)
            image.close()
            
            bitmap
        } catch (e: Exception) {
            Timber.e(e, "Error capturing frame")
            null
        }
    }

    private fun imageToBitmap(image: Image): Bitmap {
        val planes = image.planes
        val buffer = planes[0].buffer
        buffer.rewind()
        val pixelStride = planes[0].pixelStride
        val padding = planes[0].rowPadding
        val width = image.width
        val height = image.height
        val bitmap = Bitmap.createBitmap(
            width + padding / pixelStride,
            height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.copyPixelsFromBuffer(buffer)
        return bitmap
    }

    fun stopCapture() {
        imageReader?.close()
        imageReader = null
        Timber.d("Screen capture stopped")
    }
}
