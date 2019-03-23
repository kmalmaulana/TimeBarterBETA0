package com.example.timebarterbeta0.data.preferences


interface PreferenceHelper {
    fun saveUser(firebaseUser: String)

    var posting: String?
    var isLogin: Boolean
}