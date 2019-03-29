package com.example.timebarterbeta0.ui.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable

open class BaseMvpPresenter<V : BaseView> constructor(
    protected val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(),
    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : MvpPresenter<V> {

    companion object {
        val firebaseDatabase = FirebaseDatabase.getInstance()
    }

    var mView: V? = null
    lateinit var compositeDisposable: CompositeDisposable

    override fun onAttach(view: V) {
        this.mView = view
        compositeDisposable = CompositeDisposable()
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.dispose()
    }
}