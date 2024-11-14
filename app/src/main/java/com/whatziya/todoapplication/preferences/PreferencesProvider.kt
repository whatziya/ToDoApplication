package com.whatziya.todoapplication.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesProvider @Inject constructor(
    private val preferences: SharedPreferences
) {
    var showCompleted: Boolean by preferences.boolean(true)
    var accessToken: String by preferences.string("Nimrodel")
    fun clear() {
        preferences.edit().clear().apply()
    }
}