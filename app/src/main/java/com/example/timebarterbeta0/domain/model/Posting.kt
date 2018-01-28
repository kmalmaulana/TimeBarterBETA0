package com.example.timebarterbeta0.domain.model

import android.os.Parcelable
import com.example.timebarterbeta0.utils.Kategori
import com.example.timebarterbeta0.utils.OrderState
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Suppress("PLUGIN_WARNING")
@IgnoreExtraProperties
@Parcelize
class Posting(): Parcelable{

    var uId: String? = ""
    var judul: String? = ""
    var deskripsi: String? = ""
    var jumlahWaktu: Int? = 0
    var statusOrder: OrderState = OrderState.POSTED
    var dateCreated: Long? = System.currentTimeMillis()
    var category: String? = Kategori.values()[0].name
    var receivedBy: String = ""

    constructor(
        uId: String?,
        judul: String?,
        deskripsi: String?,
        jumlahWaktu: Int?,
        statusOrder: OrderState,
        dateCreated: Long?,
        category: String?,
        receivedBy: String
    ):this() {
        this.uId = uId
        this.judul = judul
        this.deskripsi = deskripsi
        this.jumlahWaktu = jumlahWaktu
        this.statusOrder = statusOrder
        this.dateCreated = dateCreated
        this.category = category
        this.receivedBy = receivedBy
    }

    init {
        uId = ""
        judul = ""
        deskripsi = ""
        jumlahWaktu = 0
        statusOrder = OrderState.POSTED
        dateCreated = System.currentTimeMillis()
        category = Kategori.UMUM.toString()
        receivedBy = ""
    }
}