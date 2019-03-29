package com.example.timebarterbeta0.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("PLUGIN_WARNING")
@Parcelize
class User():Parcelable{
    var Name: String?
    var Email: String?
    var Password: String?
    var timeBalance: Long?
    var photoProfile: String?
    var point: Int?
    var level: String?

    constructor(nama: String?, email: String?, password: String?, timeBalance: Long?, photoProfile: String?, point: Int?, level: String) : this() {
        this.Name = nama
        this.Email = email
        this.Password = password
        this.timeBalance = timeBalance
        this.photoProfile = photoProfile
        this.point = point
        this.level = level
    }

    init {
        Name = ""
        Email = ""
        Password = ""
        timeBalance = 5
        photoProfile = ""
        point = 0
        level = "0"
    }
}
