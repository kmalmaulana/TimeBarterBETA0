package com.example.timebarterbeta0.utils.extentions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

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

fun DatabaseReference.listenerValueEvent(onCancel: (DatabaseError) -> Unit = {},
                                         onChanged: (DataSnapshot) -> Unit){
    this.addValueEventListener(object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            onCancel(p0)
        }

        override fun onDataChange(p0: DataSnapshot) {
            onChanged(p0)
        }
    })
}