package com.cnar.screentranslator.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

data class CacheEntry(
    val sourceText: String,
    val translatedText: String,
    val timestamp: Long
)

class CacheManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "translation_cache",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    private val maxCacheSize = 500
    private val cacheExpiry = 24 * 60 * 60 * 1000L // 24 hours in milliseconds

    fun get(sourceText: String): String? {
        return try {
            val json = preferences.getString(sourceText, null) ?: return null
            val entry = gson.fromJson(json, CacheEntry::class.java)
            
            // Check if cache entry has expired
            if (System.currentTimeMillis() - entry.timestamp > cacheExpiry) {
                remove(sourceText)
                return null
            }
            
            Timber.d("Cache hit: $sourceText")
            entry.translatedText
        } catch (e: Exception) {
            Timber.e(e, "Error retrieving cache for: $sourceText")
            null
        }
    }

    fun put(sourceText: String, translatedText: String) {
        try {
            // Check cache size and remove oldest entry if needed
            if (preferences.all.size >= maxCacheSize) {
                val oldestKey = preferences.all.minByOrNull { (key, value) ->
                    try {
                        val entry = gson.fromJson(value.toString(), CacheEntry::class.java)
                        entry.timestamp
                    } catch (e: Exception) {
                        Long.MAX_VALUE
                    }
                }?.key
                
                oldestKey?.let { remove(it) }
            }
            
            val entry = CacheEntry(
                sourceText = sourceText,
                translatedText = translatedText,
                timestamp = System.currentTimeMillis()
            )
            val json = gson.toJson(entry)
            preferences.edit().putString(sourceText, json).apply()
            
            Timber.d("Cache stored: $sourceText -> $translatedText")
        } catch (e: Exception) {
            Timber.e(e, "Error storing cache for: $sourceText")
        }
    }

    fun remove(sourceText: String) {
        preferences.edit().remove(sourceText).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
        Timber.d("Cache cleared")
    }

    fun getCacheStats(): Map<String, Any> {
        return mapOf(
            "size" to preferences.all.size,
            "maxSize" to maxCacheSize
        )
    }
}
