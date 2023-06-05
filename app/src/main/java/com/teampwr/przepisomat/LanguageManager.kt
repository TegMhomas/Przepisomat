package com.teampwr.przepisomat

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageManager {
    private const val LANGUAGE_KEY = "selected_language"

    fun getSelectedLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(LANGUAGE_KEY, null)
    }

    fun setSelectedLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LANGUAGE_KEY, language)
        editor.apply()
    }

    fun applyLanguage(context: Context) {
        val selectedLanguage = getSelectedLanguage(context)
        selectedLanguage?.let {
            val locale = Locale(it)
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = Configuration(resources.configuration)
            configuration.setLocale(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}