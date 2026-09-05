package com.cnar.screentranslator.manager

import android.content.Context
import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

data class RecognizedText(
    val text: String,
    val boundingBox: android.graphics.Rect,
    val confidence: Float
)

class OcrManager(private val context: Context) {
    private val recognizer = TextRecognition.getClient(
        ChineseTextRecognizerOptions.Builder().build()
    )

    suspend fun recognizeText(bitmap: Bitmap): List<RecognizedText> = withContext(Dispatchers.Default) {
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            val result = recognizer.process(image)
            
            val recognizedTexts = mutableListOf<RecognizedText>()
            
            for (block in result.textBlocks) {
                for (line in block.lines) {
                    val text = line.text
                    val boundingBox = line.boundingBox ?: continue
                    val confidence = line.confidence
                    
                    if (text.isNotEmpty() && isChinese(text)) {
                        recognizedTexts.add(
                            RecognizedText(
                                text = text,
                                boundingBox = boundingBox,
                                confidence = confidence
                            )
                        )
                    }
                }
            }
            
            Timber.d("Recognized ${recognizedTexts.size} Chinese text blocks")
            recognizedTexts
        } catch (e: Exception) {
            Timber.e(e, "Error recognizing text")
            emptyList()
        }
    }

    private fun isChinese(text: String): Boolean {
        val chinesePattern = "[\u4E00-\u9FFF]+".toRegex()
        return chinesePattern.containsMatchIn(text)
    }

    fun close() {
        recognizer.close()
    }
}
