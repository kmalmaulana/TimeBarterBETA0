package com.example.timebarterbeta0.ui.main.beranda.detail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.beranda.BerandaFragment
import com.example.timebarterbeta0.utils.extentions.loadImage
import kotlinx.android.synthetic.main.activity_detail_post.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailPostActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val posting = intent.getParcelableExtra<Posting>(BerandaFragment.POSTING_EXTRA)
        tv_nama_detail.text = posting.idUser
        tv_judul_detail.text = posting.judul
        tv_deskripsi_detail.text = posting.deskripsi

        val calendar: Date? = Calendar.getInstance().time
        calendar?.time = posting.dateCreated!!
        val dateFormat: DateFormat = object : SimpleDateFormat("yyyy-MM-dd hh:mm:ss"){}
        val date: String = dateFormat.format(calendar)

        tv_dateCreate_detail.text = date
        tv_jumlahWaktu_detail.text = posting.jumlahWaktu.toString()
        tv_kategori_detail.text = posting.category
    }

    override fun initLayout(): Int = R.layout.activity_detail_post

}
