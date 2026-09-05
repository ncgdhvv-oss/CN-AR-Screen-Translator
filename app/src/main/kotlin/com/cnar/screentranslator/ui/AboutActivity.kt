package com.cnar.screentranslator.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cnar.screentranslator.R
import com.cnar.screentranslator.BuildConfig

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_title)

        val versionText = findViewById<TextView>(R.id.version_text)
        versionText.text = getString(R.string.about_version, BuildConfig.VERSION_NAME)
    }
}
