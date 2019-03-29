package com.example.timebarterbeta0.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

abstract class BaseActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        database = FirebaseDatabase.getInstance()
    }

    abstract fun initLayout(): Int
}