package com.example.timebarterbeta0.utils.extentions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.database.Query

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.loadImage(url:String){
    Glide.with(context)
        .load(url)
        .into(this)
}