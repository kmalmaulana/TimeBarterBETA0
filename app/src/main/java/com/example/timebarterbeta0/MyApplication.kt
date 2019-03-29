package com.example.timebarterbeta0

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val firebaseApp = FirebaseApp.initializeApp(this)
        firebaseDatabase = firebaseApp?.let { app ->
            FirebaseDatabase.getInstance(app)
        }!!
        firebaseDatabase.setPersistenceEnabled(true)


        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
//        startKoin(this, listOf(presenterModule))
    }

    companion object {
        lateinit var firebaseDatabase: FirebaseDatabase
    }
}