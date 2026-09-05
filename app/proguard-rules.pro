# General Android proguard rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.** { *; }
-dontwarn kotlin.**

# ML Kit
-keep class com.google.mlkit.** { *; }
-keepclassmembers class com.google.mlkit.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# GSON
-keep class com.google.gson.** { *; }
-keepclassmembers class com.google.gson.** { *; }

# Google Cloud
-keep class com.google.cloud.** { *; }
-keepclassmembers class com.google.cloud.** { *; }

# Reflection for translation API
-keepclasseswithmembernames class * {
    native <methods>;
}
