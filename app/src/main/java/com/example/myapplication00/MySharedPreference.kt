package com.example.myapplication00

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    companion object {

        val PREFERENCES_NAME = "pref"
        val DEFAULT_VALUE_BOOLEAN = false

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }

        fun getBoolean(context: Context?, key: String?): Boolean {
            val prefs = getPreferences(context!!)
            return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
        }

        fun setBoolean(context: Context?, key: String?, value: Boolean) {
            val prefs: SharedPreferences = getPreferences(context!!)
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.commit()
        }
    }
}