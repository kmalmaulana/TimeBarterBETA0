package com.example.timebarterbeta0.ui.main.beranda

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.domain.User
import com.example.timebarterbeta0.utils.extentions.loadImage
import kotlinx.android.synthetic.main.row_list_post.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BerandaAdapter(private val listPosting: MutableList<Posting>,private val listener:(Posting)->Unit): RecyclerView.Adapter<BerandaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_list_post,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listPosting[position],listener)
    }

    override fun getItemCount(): Int {
        return listPosting.size
    }

    inner class ViewHolder(val view:View): RecyclerView.ViewHolder(view) {
        fun bind(
            posting: Posting,
            listener: (Posting) -> Unit
        ) {
            val calendar: Date? = Calendar.getInstance().time
            calendar?.time = posting.dateCreated!!
            val dateFormat: DateFormat = object : SimpleDateFormat("yyyy-MM-dd hh:mm:ss"){}
            val date: String = dateFormat.format(calendar)
            val user = User()
            with(view){
                textView_user_post.text = user.Name
                posting.idUser?.let { imageView2.loadImage(it) }
                textView_date_create_post.text = date
                textView_judul_post.text = posting.judul
                textView_deskripsi_post.text = posting.deskripsi
                textView_jumlah_waktu_post.text = posting.jumlahWaktu.toString()
                textView_category.text = posting.category
                this.setOnClickListener {
                    listener(posting)
                }
            }
        }
    }

}