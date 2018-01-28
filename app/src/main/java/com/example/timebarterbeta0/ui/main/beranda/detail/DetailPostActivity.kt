package com.example.timebarterbeta0.ui.main.beranda.detail

import android.os.Bundle
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.beranda.BerandaFragment
import kotlinx.android.synthetic.main.activity_detail_post.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailPostActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val posting = intent.getParcelableExtra<Posting?>(BerandaFragment.POSTING_EXTRA)
        val date: String = settingTime(posting)
        bindView(posting, date)
    }

    private fun bindView(posting: Posting?, date: String) {
        tv_nama_detail.text = posting?.uId
        tv_judul_detail.text = posting?.judul
        tv_deskripsi_detail.text = posting?.deskripsi
        tv_dateCreate_detail.text = date
        tv_jumlahWaktu_detail.text = posting?.jumlahWaktu.toString()
        tv_kategori_detail.text = posting?.category
    }

    private fun settingTime(posting: Posting?): String {
        val calendar: Date? = Calendar.getInstance().time
        posting?.let { calendar?.time = it.dateCreated!! }
        val dateFormat: DateFormat = object : SimpleDateFormat("dd-MM-yyyy hh:mm"){}
        return dateFormat.format(calendar)
    }

    override fun initLayout(): Int = R.layout.activity_detail_post

}
