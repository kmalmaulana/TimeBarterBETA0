package com.example.timebarterbeta0.data.FirebaseHelper

import com.example.timebarterbeta0.domain.User

interface Firebasehelper {

    fun getAllUser(): List<User>
    fun login():User
    fun register()
    fun getPosting()

}
