package com.example.timebarterbeta0.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
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
    var listPosting: List<Posting>?

    constructor(nama: String?, email: String?, password: String?, timeBalance: Long?, photoProfile: String?, point: Int?, level: String, listPosting: List<Posting>) : this() {
        this.Name = nama
        this.Email = email
        this.Password = password
        this.timeBalance = timeBalance
        this.photoProfile = photoProfile
        this.point = point
        this.level = level
        this.listPosting = listPosting
    }

    init {
        Name = ""
        Email = ""
        Password = ""
        timeBalance = 5
        photoProfile = ""
        point = 0
        level = "0"
        listPosting = listOf()
    }
}
