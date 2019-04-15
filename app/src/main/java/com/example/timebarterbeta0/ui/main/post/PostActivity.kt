package com.example.timebarterbeta0.ui.main.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.utils.Kategori
import com.example.timebarterbeta0.utils.OrderState
import kotlinx.android.synthetic.main.fragment_post.*

class PostActivity : AppCompatActivity(), PostContract.PostView{

    private lateinit var presenter: PostMvpPresenter
    var category = Kategori.UMUM.toString()

    override fun showSuccessMessage() {
        Toast.makeText(this, "Post uploaded", Toast.LENGTH_SHORT).show()
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

        presenter = PostMvpPresenter()
        presenter.onAttach(this)

        initSpinnerView()
        btn_posting.setOnClickListener { posting() }
        val jumlahWaktu = et_jumlahWaktu.text.toString()

        val jumlahWaktuInteger = Integer.parseInt(jumlahWaktu)

        countTime(jumlahWaktuInteger)
    }

    private fun countTime(jumlahWaktuInteger: Int) {
        var jumlahWaktuInteger1 = jumlahWaktuInteger
        btn_add_time.setOnClickListener {
            jumlahWaktuInteger1 += 1
            et_jumlahWaktu.text = jumlahWaktuInteger1.toString()
        }

        btn_min_time.setOnClickListener {
            jumlahWaktuInteger1 -= 1
            et_jumlahWaktu.text = jumlahWaktuInteger1.toString()
        }
    }

    private fun posting() {
        val judul = et_judul.text.toString()
        val deskripsi = et_deskripsi.text.toString()
        val jumlahWaktu: Int = Integer.parseInt(et_jumlahWaktu.text.toString())

        posting(judul, deskripsi, jumlahWaktu)
    }

    private fun initSpinnerView() {
        val arrayAdapter =
            ArrayAdapter.createFromResource(this, R.array.spinnerKategori, android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun posting(judul: String, description: String, jumlahWaktu: Int) {

        if(judul.isEmpty()&&description.isEmpty()){
            et_judul.error = "Please enter the title"
            et_deskripsi.error = "Please enter description"
        }

        presenter.setPostingFirebase(
            judul,
            description,
            jumlahWaktu,
            OrderState.POSTED,
            System.currentTimeMillis(),
            category
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
