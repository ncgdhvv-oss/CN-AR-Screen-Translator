# دليل الإعدادات المتقدمة

## 🔧 إعدادات Google Cloud API

### الخطوة 1: إنشاء حساب Google Cloud

1. اذهب إلى [Google Cloud Console](https://console.cloud.google.com)
2. سجل دخول بحسابك على Google
3. اقرأ شروط الخدمة وافق عليها

### الخطوة 2: إنشاء مشروع جديد

1. من القائمة العلوية اليسرى، اختر "Select a Project"
2. اضغط "NEW PROJECT"
3. أدخل اسم المشروع: `CN-AR-Translator`
4. اختر المجلد (Organization) إن لزم
5. اضغط "CREATE"

### الخطوة 3: تفعيل Translation API

1. في Dashboard، ابحث عن "Translation API"
2. اضغط على النتيجة
3. اضغط "ENABLE" (تفعيل)
4. انتظر حتى ينتهي التفعيل

### الخطوة 4: إنشاء API Key

1. اذهب إلى "Credentials" من الجانب الأيسر
2. اضغط "+ CREATE CREDENTIALS"
3. اختر "API Key"
4. سيظهر مفتاحك الجديد في نافذة منبثقة
5. انسخ المفتاح (Copy)

### الخطوة 5: تأمين المفتاح (اختياري لكن مهم)

1. اضغط على المفتاح الذي أنشأته
2. تحت "API restrictions"، اختر "Cloud Translation API"
3. تحت "Application restrictions"، اختر "Android apps"
4. أضف حزمة التطبيق: `com.cnar.screentranslator`
5. اضغط "SAVE"

### الخطوة 6: إدخال المفتاح في التطبيق

1. افتح التطبيق → الإعدادات
2. ابحث عن "مفتاح API"
3. الصق المفتاح الذي نسخته
4. اضغط حفظ

---

## 💰 الفواتير والحدود

### اختبار مجاني (Free Tier)

- **500,000 حرف في الشهر مجاناً** (بعد الحرف الأول)
- **بدون بطاقة ائتمان في أول 3 أشهر**
- يجب إضافة بطاقة ائتمان بعد ذلك

### تحديد الحدود (Important)

1. اذهب إلى "Quotas"
2. اختر "Cloud Translation API"
3. اضغط "Edit Quotas"
4. حدد الحد الأقصى (مثلاً: 100,000 حرف/يوم)
5. اضغط "Next" ثم "Save"

---

## 🎨 تخصيص الواجهة

### تغيير الألوان

1. افتح `app/src/main/res/values/colors.xml`
2. عدّل الألوان الأساسية:
   ```xml
   <color name="primary_color">#2196F3</color>      <!-- الأزرق الأساسي -->
   <color name="secondary_color">#FF6F00</color>    <!-- البرتقالي -->
   <color name="overlay_text">#FFFFFF</color>       <!-- أبيض للنص -->
   <color name="overlay_background">#1A1A1A</color> <!-- رمادي غامق -->
   ```

### تغيير الخطوط

1. أضف ملف الخط في `app/src/main/res/font/`
2. استخدمه في `themes.xml`

### تخصيص حجم الخط الافتراضي

1. افتح `app/src/main/res/values/strings.xml`
2. غيّر: `<string name="default_font_size">16</string>`

---

## 🔐 نصائح الأمان

### إخفاء مفتاح API

**لا تضع المفتاح في الكود مباشرة!**

الطريقة الآمنة:

1. في `local.properties`:
   ```properties
   GOOGLE_TRANSLATE_API_KEY=your_key_here
   ```

2. في `build.gradle`:
   ```gradle
   buildTypes {
       release {
           buildConfigField "String", "API_KEY", '"' + getApiKey() + '"'
       }
   }
   ```

3. في الكود:
   ```kotlin
   val apiKey = BuildConfig.API_KEY
   ```

---

## ⚙️ إعدادات الأداء

### تقليل استهلاك البطارية

1. فترة الفحص: 1500-2000ms (بدلاً من 500ms)
2. تعطيل Keep Screen On
3. تقليل حجم الخط
4. تقليل دقة الالتقاط

### تحسين السرعة

1. فترة الفحص: 500-800ms
2. تفعيل GPU Optimization
3. زيادة عدد المعالجات (Threads)
4. تفعيل Cache

---

## 📱 دعم أجهزة متعددة

### اختبار على أجهزة مختلفة

```bash
# قائمة الأجهزة المتصلة
adb devices

# تثبيت على جهاز معين
adb -s <device_id> install app.apk

# عرض السجلات
adb -s <device_id> logcat
```

---

## 🧪 الاختبار والتصحيح

### تفعيل Logging

```kotlin
// في App.kt
if (BuildConfig.DEBUG) {
    Timber.plant(Timber.DebugTree())
} else {
    Timber.plant(CrashReportingTree())
}
```

### عرض السجلات

```bash
# من Android Studio: View → Tool Windows → Logcat
# أو من Terminal:
adb logcat | grep "CN-AR-Screen-Translator"
```

---

## 🚀 نشر الإصدار النهائي

### توقيع APK

```bash
# إنشاء Keystore
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias cnar_translator

# التوقيع
jarsigner -verbose -sigalg SHA256withRSA \
  -digestalg SHA-256 \
  -keystore release.keystore \
  app-release-unsigned.apk cnar_translator

# التحسين
zipalign -v 4 app-release-unsigned.apk app-release.apk
```

### نشر على Google Play (إن أردت)

1. أنشئ حسابًا على [Google Play Console](https://play.google.com/console)
2. أنشئ تطبيقًا جديدًا
3. أرفع الـ APK الموقّع
4. أضف الصور والوصف
5. اضغط "Release"

---

**آخر تحديث:** 2026-09-05
