package com.example.timebarterbeta0.ui.base

import com.example.timebarterbeta0.MyApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseMvpPresenter<V : BaseView> constructor(
    protected val firebaseDatabase: FirebaseDatabase = MyApplication.firebaseDatabase,
    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : MvpPresenter<V>, CoroutineScope {

    private var job:Job = Job()
    companion object {

        val firebaseDatabase = FirebaseDatabase.getInstance()

    }

    init {
        firebaseDatabase.setPersistenceEnabled(true)
    }

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
}