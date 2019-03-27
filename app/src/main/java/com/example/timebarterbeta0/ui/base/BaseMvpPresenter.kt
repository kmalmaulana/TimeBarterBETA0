package com.example.timebarterbeta0.ui.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseMvpPresenter<V : BaseView> constructor(
    protected val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(),
    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : MvpPresenter<V>, CoroutineScope {

    private val job= Job()
    companion object {

        val firebaseDatabase = FirebaseDatabase.getInstance()

    }

    final override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val uiScope: CoroutineScope = CoroutineScope(this.coroutineContext)

    var mView: V? = null
    lateinit var compositeDisposable: CompositeDisposable

    override fun onAttach(view: V) {
        this.mView = view
        compositeDisposable = CompositeDisposable() }

    override fun onDetach() {
        mView = null
        compositeDisposable.dispose()
        job.cancel()
    }
}