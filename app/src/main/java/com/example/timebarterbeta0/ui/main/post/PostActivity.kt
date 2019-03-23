package com.example.timebarterbeta0.ui.main.post

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.utils.Kategori
import com.example.timebarterbeta0.utils.OrderState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.login_activity.*

class PostActivity : AppCompatActivity(), PostContract.PostView{

    lateinit var presenter: PostMvpPresenter

    override fun showSuccesMessage() {
        Toast.makeText(this, "Hore", Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun showLoading() {

    }

    override fun hideLoading() {

    }
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_post)

        btn_posting.setOnClickListener {

            val judul = et_judul.text.toString()
            val deskripsi  = et_deskripsi.text.toString()
            val jumlahWaktu : Int = Integer.parseInt(et_jumlahWaktu.text.toString())

            posting(judul, deskripsi, jumlahWaktu)

        }
        presenter = PostMvpPresenter()
        val jumlahWaktu = et_jumlahWaktu.text.toString()

        var jumlahWaktuInteger = Integer.parseInt(jumlahWaktu)

        btn_add_time.setOnClickListener {
            jumlahWaktuInteger += 1
            et_jumlahWaktu.text = jumlahWaktuInteger.toString()
        }

        btn_min_time.setOnClickListener {
            jumlahWaktuInteger -= 1
            et_jumlahWaktu.text = jumlahWaktuInteger.toString()


        }
    }

    private fun posting(judul: String, deskripsi: String, jumlahWaktu: Int) {
        val firebaseAuth = FirebaseAuth.getInstance()

        if(judul.isEmpty()&&deskripsi.isEmpty()){
            et_judul.error = "Please enter judul bro"
            et_deskripsi.error = "Please enter deskripsi dulu setan"
        }
        val post = Posting(firebaseAuth.currentUser?.displayName,judul,deskripsi, jumlahWaktu,OrderState.POSTED,System.currentTimeMillis(),Kategori.UMUM.toString(),"")
        presenter.setPostingFirebase(post)

    }
}
