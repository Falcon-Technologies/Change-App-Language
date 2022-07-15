package com.falcon.technologies.utils

import android.content.Context

class PrefValues {

    private var prefName = "sharePreference"


    fun getLangPref(context: Context, filename: String?): Int {
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(filename, 0)
    }

    fun insertLangPref(context: Context, filename: String?, value: Int) {
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(filename, value)
        editor.apply()
    }

    fun setValue(context: Context, filename: String?, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(filename, value)
        editor.apply()
    }

    fun getValue(context: Context, filename: String?): Boolean {
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(filename, false)
    }

}