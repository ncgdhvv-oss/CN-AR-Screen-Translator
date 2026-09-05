# Contributing Guide

شكراً لاهتمامك بالمساهمة في هذا المشروع! 🎉

## كيفية المساهمة

### الإبلاغ عن الأخطاء 🐛

1. افتح [Issues](https://github.com/ncgdhvv-oss/CN-AR-Screen-Translator/issues)
2. اضغط "New Issue"
3. اختر "Bug Report"
4. صف المشكلة بالتفصيل:
   - خطوات التكرار
   - السلوك المتوقع والفعلي
   - سجل الأخطاء (Log)
   - إصدار Android والهاتف

### اقتراح ميزات جديدة ✨

1. افتح Issue جديدة
2. اختر "Feature Request"
3. اشرح:
   - الميزة المطلوبة
   - الفائدة المتوقعة
   - أمثلة على الاستخدام

### المساهمة بالكود 💻

#### 1. Fork المشروع
```bash
# اذهب إلى https://github.com/ncgdhvv-oss/CN-AR-Screen-Translator
# اضغط "Fork" في الزاوية العلوية اليمنى
```

#### 2. استنسخ مشروعك
```bash
git clone https://github.com/YOUR_USERNAME/CN-AR-Screen-Translator.git
cd CN-AR-Screen-Translator
```

#### 3. أنشئ فرعًا جديدًا
```bash
git checkout -b feature/your-feature-name
# أو
git checkout -b fix/your-bug-fix
```

#### 4. قم بالتغييرات
- الالتزم بأسلوب الكود الموجود
- استخدم Kotlin لأكواد Android الجديدة
- اكتب comments واضحة
- أضف Logging باستخدام Timber

#### 5. اختبر التغييرات
```bash
# بناء المشروع
./gradlew build

# تشغيل الاختبارات
./gradlew test

# تثبيت على جهاز
./gradlew installDebug
```

#### 6. أرسل التغييرات
```bash
git add .
git commit -m "Add/Fix: brief description of changes"
git push origin feature/your-feature-name
```

#### 7. افتح Pull Request
1. اذهب إلى مستودعك على GitHub
2. اضغط "Compare & pull request"
3. اختر `main` كـ base branch
4. اشرح التغييرات بالتفصيل
5. اضغط "Create pull request"

---

## معايير الكود

### Kotlin Style
```kotlin
// ✅ صحيح
class MyClass {
    fun myFunction(parameter: String): String {
        return parameter.uppercase()
    }
}

// ❌ خطأ
class my_class{
    fun my_function(param:String):String{
        return param.uppercase()
    }
}
```

### Naming Convention
- **Classes**: `PascalCase` (e.g., `OcrManager`)
- **Functions**: `camelCase` (e.g., `recognizeText`)
- **Variables**: `camelCase` (e.g., `isTranslating`)
- **Constants**: `UPPER_SNAKE_CASE` (e.g., `NOTIFICATION_ID`)
- **Private**: `_camelCase` prefix (e.g., `_imageReader`)

### Comments and Documentation
```kotlin
/**
 * وصف الدالة بالعربية أو الإنجليزية
 * @param bitmap صورة المشهد
 * @return قائمة النصوص المكتشفة
 */
suspend fun recognizeText(bitmap: Bitmap): List<RecognizedText>
```

---

## ملفات مهمة للتعديل

### إضافة ميزة جديدة
1. **UI**: عدّل الـ Layouts في `res/layout/`
2. **Logic**: أضف الكود في `kotlin/...`
3. **Resources**: أضف الـ Strings في `strings.xml`
4. **Styling**: عدّل `themes.xml` و `colors.xml`

### إصلاح باج
1. أضف testcase في `test/`
2. أصلح الكود في `kotlin/`
3. أضف Logging للتصحيح
4. أكمل التوثيق

---

## قائمة التحقق قبل الـ Commit

- [ ] الكود يبني بنجاح بدون أخطاء
- [ ] جميع الاختبارات تمر
- [ ] لا توجد تحذيرات Lint
- [ ] التعليقات واضحة ودقيقة
- [ ] استخدمت Timber للـ Logging
- [ ] الـ Strings في `strings.xml`
- [ ] اختبرت على Android 14+
- [ ] الأذونات موجودة في Manifest

---

## آداب التعامل

- 💬 كن محترمًا وودودًا
- 🤝 تقبل النقد البناء
- 📚 اشرح أفكارك بوضوح
- 🔍 راجع PR الآخرين
- ✅ اختبر قبل الـ Commit

---

شكرًا لمساهمتك! 🙏
