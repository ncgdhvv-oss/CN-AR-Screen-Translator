# CN → AR Screen Translator

## 📱 تطبيق ترجمة شاشة صيني-عربي فوري

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Android](https://img.shields.io/badge/android-14+-green)
![License](https://img.shields.io/badge/license-MIT-blue)
![Language](https://img.shields.io/badge/language-Kotlin%20%7C%20Java-orange)

تطبيق Android أصلي 100% يلتقط شاشة الهاتف تلقائياً ويتعرف على النصوص الصينية ويترجمها إلى العربية في الوقت الفعلي بدون الاعتماد على Pydroid 3 أو Python أو Termux.

---

## ✨ المزايا الرئيسية

✅ **التقاط شاشة حي** - استخدام MediaProjection API لالتقاط الشاشة مباشرة من Android 14

✅ **OCR دقيق** - Google ML Kit Text Recognition للصينية المبسطة والتقليدية

✅ **ترجمة فورية** - ترجمة Chinese → Arabic بكفاءة عالية

✅ **عرض Overlay ذكي** - عرض الترجمة فوق التطبيقات الأخرى بالقرب من النص الأصلي

✅ **Foreground Service** - خدمة مستمرة لا تتوقف أثناء استخدام التطبيقات الأخرى

✅ **نظام Cache** - حفظ الترجمات السابقة لتسريع الترجمة وتقليل استهلاك الإنترنت

✅ **إعدادات مرنة** - التحكم في حجم الخط والشفافية والألوان والموضع

✅ **فترة فحص قابلة للتعديل** - من 500ms إلى 2000ms حسب الحاجة

✅ **دعم اتجاهات الشاشة** - يعمل في Portrait و Landscape

✅ **استهلاك منخفض** - تحسين الأداء لتقليل استهلاك البطارية والذاكرة

---

## 🚀 البدء السريع

### المتطلبات
- **Android 14 فما فوق** (مختبر على Android 14)
- **Android Studio 2023.1 أو أحدث**
- **Java 11 أو Kotlin 1.9.10**
- **Google Cloud Translation API** (للترجمة الأونلاين)

### خطوات التثبيت والبناء

#### 1️⃣ استنساخ المشروع
```bash
git clone https://github.com/ncgdhvv-oss/CN-AR-Screen-Translator.git
cd CN-AR-Screen-Translator
```

#### 2️⃣ فتح المشروع في Android Studio
```bash
# خيار 1: من سطر الأوامر
android-studio .

# خيار 2: يدوياً
# افتح Android Studio → Open → اختر مجلد المشروع
```

#### 3️⃣ تحديث Gradle
اترك Android Studio يقوم بتحديث Gradle Wrapper تلقائياً عند أول فتح.

#### 4️⃣ تثبيت المكتبات
```bash
# سيتم تحميل جميع الـ Dependencies تلقائياً من Google Maven و Maven Central
```

#### 5️⃣ إعداد Google Cloud Translation API
```bash
# أنشئ حساب Google Cloud
1. اذهب إلى https://console.cloud.google.com
2. أنشئ مشروع جديد
3. فعّل "Cloud Translation API"
4. اذهب إلى "Credentials"
5. أنشئ "API Key"
6. انسخ المفتاح (API Key)
7. افتح التطبيق → الإعدادات → الصق مفتاح API
```

#### 6️⃣ بناء APK
```bash
# طريقة 1: من Android Studio
# Build → Build Bundles/APK → Build APK(s)

# طريقة 2: من سطر الأوامر
./gradlew build          # بناء debug + release
./gradlew assembleDebug  # بناء debug فقط
./gradlew assembleRelease # بناء release مع توقيع
```

سيكون الـ APK في: `app/build/outputs/apk/`

#### 7️⃣ تثبيت APK على الهاتف
```bash
# عبر Android Studio
adb install -r app/build/outputs/apk/debug/app-debug.apk

# أو من الملفات:
# 1. انقل ملف APK إلى الهاتف
# 2. افتح File Manager
# 3. اضغط على الملف → Install
```

---

## 🎮 طريقة الاستخدام

### الخطوات الأولى

1. **تثبيت التطبيق على الهاتف**
   ```
   اضغط على ملف APK → Install
   ```

2. **فتح التطبيق**
   ```
   ابحث عن "CN → AR Screen Translator" في قائمة التطبيقات
   ```

3. **الموافقة على الأذونات**
   ```
   - توافق على "Internet" (للترجمة)
   - توافق على "Foreground Service" (للخدمة المستمرة)
   ```

4. **السماح بـ Overlay**
   ```
   - اذهب إلى الإعدادات → التطبيقات → CN → AR Screen Translator
   - اختر "Advanced" → "Display over other apps"
   - فعّل الخيار
   ```

5. **بدء الترجمة**
   ```
   - اضغط زر "بدء الترجمة"
   - وافق على التقاط الشاشة (يطلب منك Android)
   - سيظهر زر عائم على الشاشة
   ```

6. **استخدام المحاكي السحابي أو اللعبة**
   ```
   - افتح المحاكي أو اللعبة كالمعتاد
   - عندما يظهر نص صيني:
     * سيتعرف التطبيق عليه تلقائياً
     * سيترجمه إلى العربية
     * ستظهر الترجمة فوق النص الأصلي
   ```

7. **إيقاف الترجمة**
   ```
   - اضغط على الزر العائم → "إيقاف"
   - أو اضغط الزر في التطبيق الرئيسي
   ```

### الإعدادات المتقدمة

#### إعدادات OCR
- **فترة الفحص**: اختر من 500ms إلى 2000ms
- **حجم المنطقة**: يمكن تحديد مربع معين للفحص

#### إعدادات الترجمة
- **مفتاح API**: أدخل مفتاح Google Cloud
- **تفعيل Cache**: حفظ الترجمات السابقة

#### إعدادات العرض
- **حجم الخط**: من 8sp إلى 32sp
- **شفافية الخلفية**: من 0% إلى 100%
- **لون النص**: اختر الأبيض أو الأسود
- **موضع الترجمة**: أسفل أو أعلى أو جانب النص

---

## 🔐 الأذونات المطلوبة

### شرح كل إذن ولماذا نحتاجه

| الإذن | الاستخدام | الضرورة |
|------|----------|--------|
| `INTERNET` | الاتصال بـ Google Translation API | ✅ مطلوب |
| `SYSTEM_ALERT_WINDOW` | عرض Overlay فوق التطبيقات | ✅ مطلوب |
| `FOREGROUND_SERVICE` | تشغيل الخدمة المستمرة | ✅ مطلوب |
| `WAKE_LOCK` | منع إطفاء الشاشة | ⚠️ اختياري |
| `ACCESS_NETWORK_STATE` | التحقق من الإنترنت | ✅ مطلوب |
| `VIBRATE` | اهتزاز عند البدء/الإيقاف | ⚠️ اختياري |

### كيفية منح الأذونات يدوياً

1. **الأذونات التطبيقية**
   ```
   الإعدادات → التطبيقات → CN → AR Screen Translator → الأذونات
   ```

2. **عرض Overlay**
   ```
   الإعدادات → التطبيقات → CN → AR Screen Translator → خيارات متقدمة → عرض فوق التطبيقات الأخرى
   ```

3. **التقاط الشاشة**
   ```
   عند بدء الترجمة لأول مرة، سيطلب منك Android إذن
   ```

---

## 📊 هيكل المشروع

```
CN-AR-Screen-Translator/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   └── com/cnar/screentranslator/
│   │   │   │       ├── ui/
│   │   │   │       │   ├── MainActivity.kt          # الواجهة الرئيسية
│   │   │   │       │   ├── SettingsActivity.kt      # إعدادات التطبيق
│   │   │   │       │   └── AboutActivity.kt         # معلومات التطبيق
│   │   │   │       ├── service/
│   │   │   │       │   ├── TranslationService.kt    # الخدمة الرئيسية
│   │   │   │       │   └── ScreenCaptureThread.kt   # خيط التقاط الشاشة
│   │   │   │       ├── manager/
│   │   │   │       │   ├── OcrManager.kt            # إدارة OCR
│   │   │   │       │   ├── TranslationManager.kt    # إدارة الترجمة
│   │   │   │       │   ├── OverlayManager.kt        # إدارة Overlay
│   │   │   │       │   ├── CacheManager.kt          # إدارة الـ Cache
│   │   │   │       │   └── ScreenCaptureManager.kt  # إدارة التقاط الشاشة
│   │   │   │       ├── util/
│   │   │   │       │   └── PermissionHelper.kt      # مساعد الأذونات
│   │   │   │       └── App.kt                       # فئة التطبيق
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_settings.xml
│   │   │   │   │   └── activity_about.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   └── arrays.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── button_background.xml
│   │   │   │   │   └── edittext_background.xml
│   │   │   │   ├── xml/
│   │   │   │   │   └── preferences.xml
│   │   │   │   └── mipmap/
│   │   │   │       └── ic_launcher*.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   │       └── java/
│   ├── build.gradle                # إعدادات البناء
│   └── proguard-rules.pro          # قواعد ProGuard
├── build.gradle                    # البناء الرئيسي
├── settings.gradle                 # إعدادات المشروع
├── .gitignore
└── README.md                       # هذا الملف
```

---

## 🛠️ المكتبات المستخدمة

### Android Core
- `androidx.appcompat:appcompat:1.6.1`
- `androidx.core:core:1.12.0`
- `androidx.constraintlayout:constraintlayout:2.1.4`

### Material Design
- `com.google.android.material:material:1.11.0`

### ML & AI
- `com.google.mlkit:text-recognition-chinese:16.0.0` - OCR الصينية
- `com.google.mlkit:text-recognition:16.0.0` - OCR الإنجليزية
- `com.google.cloud:google-cloud-translate:2.3.1` - ترجمة Google

### Async Programming
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3`

### Networking
- `com.squareup.okhttp3:okhttp:4.11.0` - HTTP Client
- `com.google.code.gson:gson:2.10.1` - JSON Parsing

### Logging
- `com.jakewharton.timber:timber:5.0.1` - Logging Framework

### Lifecycle
- `androidx.lifecycle:lifecycle-runtime-ktx:2.6.2`
- `androidx.lifecycle:lifecycle-service:2.6.2`

---

## 🐛 استكشاف الأخطاء

### المشكلة: التطبيق لا يبدأ
**الحل:**
1. تأكد من تثبيت Android 14 أو أحدث
2. امسح ذاكرة التطبيق: الإعدادات → التطبيقات → CN → AR → تخزين → حذف البيانات
3. أعد تثبيت التطبيق

### المشكلة: لا تظهر الترجمة
**الحل:**
1. تحقق من الأذونات: الإعدادات → التطبيقات → الأذونات
2. تأكد من تفعيل Overlay: الإعدادات → التطبيقات → CN → Display over other apps
3. تحقق من API Key في الإعدادات
4. تأكد من الاتصال بالإنترنت

### المشكلة: الترجمة بطيئة
**الحل:**
1. قلل فترة الفحص من 2000ms إلى 800ms
2. تفعيل تحسين GPU من الإعدادات
3. أغلق التطبيقات الأخرى التي تستهلك الذاكرة
4. امسح cache التطبيق

### المشكلة: استهلاك بطارية عالي
**الحل:**
1. زيادة فترة الفحص إلى 1500ms أو 2000ms
2. قلل حجم الخط
3. قلل التحديث تلقائي
4. عطّل Keep Screen On

### المشكلة: تطبيق Google Cloud API لا يعمل
**الحل:**
1. تحقق من أن API Key صحيح
2. تأكد من تفعيل Translation API في Google Console
3. تحقق من حدود الطلبات (billing)
4. استخدم Fallback Dictionary (بدون إنترنت)

---

## 📈 التحسينات المستقبلية

- [ ] دعم OCR بدون إنترنت
- [ ] نماذج ترجمة محلية Offline
- [ ] دعم صور متعددة على الشاشة
- [ ] تسجيل الترجمات والإحصائيات
- [ ] Dark Mode محسّن
- [ ] دعم لغات أخرى
- [ ] محرر النصوص المترجمة
- [ ] مشاركة الترجمات

---

## 🤝 المساهمة

نرحب بالمساهمات! يرجى:

1. Fork المشروع
2. أنشئ فرع للميزة الجديدة (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add AmazingFeature'`)
4. Push للفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

---

## 📜 الترخيص

هذا المشروع مرخص تحت **MIT License** - انظر ملف [LICENSE](LICENSE) للتفاصيل.

---

## 👨‍💻 المطور

**NCG DHVV**
- GitHub: [@ncgdhvv-oss](https://github.com/ncgdhvv-oss)
- Email: ncgdhvv@gmail.com

---

## 📞 الدعم

إذا واجهت مشكلة:

1. تحقق من قسم [استكشاف الأخطاء](#-استكشاف-الأخطاء)
2. ابحث في [Issues](https://github.com/ncgdhvv-oss/CN-AR-Screen-Translator/issues)
3. افتح [Issue جديدة](https://github.com/ncgdhvv-oss/CN-AR-Screen-Translator/issues/new) مع شرح دقيق

---

## 🙏 شكر وتقدير

- Google ML Kit للتعرف على النصوص الصينية
- Google Cloud Translation للترجمة
- مجتمع Android العربي

---

**آخر تحديث:** 2026-09-05
**الإصدار:** 1.0.0
