package com.cnar.screentranslator.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.cloud.translate.Translate.TranslateOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import timber.log.Timber

class TranslationManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "translation_prefs",
        Context.MODE_PRIVATE
    )
    private val httpClient = OkHttpClient()
    private val translationCache = mutableMapOf<String, String>()
    private val maxCacheSize = 500

    suspend fun translateChineseToArabic(text: String): String = withContext(Dispatchers.IO) {
        try {
            // Check cache first
            if (translationCache.containsKey(text)) {
                Timber.d("Translation found in cache: $text")
                return@withContext translationCache[text] ?: text
            }

            val apiKey = preferences.getString("google_translate_api_key", "") ?: ""
            if (apiKey.isEmpty()) {
                Timber.w("API key not configured, using fallback translation")
                return@withContext fallbackTranslate(text)
            }

            val translated = translateWithGoogle(text, apiKey)
            
            // Cache the translation
            if (translationCache.size >= maxCacheSize) {
                translationCache.remove(translationCache.keys.first())
            }
            translationCache[text] = translated

            translated
        } catch (e: Exception) {
            Timber.e(e, "Error translating text: $text")
            text
        }
    }

    private suspend fun translateWithGoogle(text: String, apiKey: String): String =
        withContext(Dispatchers.IO) {
            try {
                val url = "https://translation.googleapis.com/language/translate/v2?key=$apiKey"
                val requestBody = """{
                    "q": "$text",
                    "target": "ar",
                    "source": "zh"
                }""".toRequestBody()

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                val response = httpClient.newCall(request).execute()
                val responseBody = response.body?.string() ?: return@withContext text

                val jsonResponse = JSONObject(responseBody)
                val translations = jsonResponse.getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText")

                translations
            } catch (e: Exception) {
                Timber.e(e, "Google translation failed")
                fallbackTranslate(text)
            }
        }

    private fun fallbackTranslate(text: String): String {
        // Simple fallback translation dictionary for common Chinese words
        val dictionary = mapOf(
            "你好" to "مرحبا",
            "谢谢" to "شكراً",
            "对不起" to "آسف",
            "是" to "نعم",
            "不是" to "لا",
            "好" to "جيد",
            "坏" to "سيء",
            "大" to "كبير",
            "小" to "صغير",
            "开始" to "ابدأ",
            "结束" to "نهاية"
        )
        return dictionary[text] ?: text
    }

    fun clearCache() {
        translationCache.clear()
        Timber.d("Translation cache cleared")
    }

    fun setApiKey(key: String) {
        preferences.edit().putString("google_translate_api_key", key).apply()
    }
}
