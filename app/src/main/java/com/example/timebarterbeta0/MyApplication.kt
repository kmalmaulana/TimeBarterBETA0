package com.example.timebarterbeta0

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
//        startKoin(this, listOf(presenterModule))
    }
}