package org.ok1cdj.kchat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import org.ok1cdj.kchat.ui.AppRoot
import org.ok1cdj.kchat.ui.theme.KChatTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val vm: ChatViewModel by viewModels()

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) { super.attachBaseContext(null); return }
        // Read language synchronously from a tiny SharedPreferences mirror written by SettingsRepository.
        val lang = newBase.getSharedPreferences("locale_cache", MODE_PRIVATE)
            .getString("language", "system") ?: "system"
        val ctx = if (lang == "system") newBase else applyLocale(newBase, lang)
        super.attachBaseContext(ctx)
    }

    private fun applyLocale(base: Context, lang: String): Context {
        val locale = when (lang) {
            "cs" -> Locale("cs")
            "en" -> Locale.ENGLISH
            else -> Locale.getDefault()
        }
        Locale.setDefault(locale)
        val cfg = Configuration(base.resources.configuration)
        cfg.setLocale(locale)
        return base.createConfigurationContext(cfg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KChatTheme {
                AppRoot(vm)
            }
        }
    }

    /**
     * Persist the chosen language to the locale-cache prefs and recreate, so the
     * new locale is applied immediately (it is only read in attachBaseContext).
     */
    fun applyLanguageAndRecreate(lang: String) {
        getSharedPreferences("locale_cache", MODE_PRIVATE)
            .edit().putString("language", lang).apply()
        recreate()
    }
}
