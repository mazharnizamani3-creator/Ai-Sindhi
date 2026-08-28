package com.example.data.local

import android.content.Context
import android.content.SharedPreferences

class UserSessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ai_sindhi_session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun setLoggedIn(loggedIn: Boolean, name: String) {
        prefs.edit().putBoolean("is_logged_in", loggedIn).putString("user_name", name).apply()
    }

    fun getUserName(): String {
        return prefs.getString("user_name", "سائين") ?: "سائين"
    }

    fun getImageCount(): Int {
        return prefs.getInt("image_count", 0)
    }

    fun incrementImageCount(): Int {
        val current = getImageCount() + 1
        prefs.edit().putInt("image_count", current).apply()
        return current
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
