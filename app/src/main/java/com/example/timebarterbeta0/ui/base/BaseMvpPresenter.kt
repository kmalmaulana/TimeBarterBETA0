package com.example.timebarterbeta0.ui.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseMvpPresenter<V : BaseView> constructor(
    protected val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(),
    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : MvpPresenter<V>, CoroutineScope {

    private var job:Job = SupervisorJob()
    companion object {
        val firebaseDatabase = FirebaseDatabase.getInstance()
    }

    val uid:String
            get()= firebaseAuth.currentUser?.uid.toString()

    final override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val uiScope: CoroutineScope = CoroutineScope(this.coroutineContext)

    var mView: V? = null


    override fun onAttach(view: V) {
        this.mView = view
        job = Job()
    }

    override fun onDetach() {
        mView = null
        when {
            !job.isCancelled-> job.cancel()
        }
    }

    fun errorHandling(databaseError: DatabaseError){
        Timber.e(databaseError.message)
        Timber.e(databaseError.details)
        Timber.e(databaseError.toException())
    }
}