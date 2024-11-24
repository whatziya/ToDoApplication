package com.whatziya.todoapplication.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesProvider @Inject constructor(
    private val preferences: SharedPreferences
) {
    var showCompleted: Boolean by preferences.boolean(true)
    var lastUpdated: Long by preferences.long(0L)
    fun clear() {
        preferences.edit().clear().apply()
    }
}
