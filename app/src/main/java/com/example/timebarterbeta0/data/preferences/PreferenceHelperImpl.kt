package com.example.timebarterbeta0.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelperImpl(val context: Context, private val prefFileName: String): PreferenceHelper {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    override var isLogin: Boolean
    get() = sharedPref.getBoolean(ISLOGIN,false)
    set(value) = editor.putBoolean(ISLOGIN, value).apply()

    override var posting: String?
        get() = sharedPref.getString(POSTING,null)
        set(value) = editor.putString(POSTING,value.toString()).apply()

    override fun saveUser(firebaseUser: String) {

    }

    companion object {

        private val POSTING: String? = "posting"
        private val ISLOGIN: String = "is login"
    }
}


