package com.learn.mydiary.util.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Preferences @Inject constructor(val context: Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(USER_PREFERENCES, 0)

    fun setValue(key: String, value: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String?{
        return sharedPreferences.getString(key, "")
    }

    companion object{
        const val USER_PREFERENCES = "user_preferences"
        const val ID = "id"
        const val NAME = "name"
        const val TOKEN = "token"
    }
}