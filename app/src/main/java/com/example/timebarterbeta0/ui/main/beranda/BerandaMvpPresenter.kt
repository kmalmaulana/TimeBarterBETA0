package com.example.timebarterbeta0.ui.main.beranda

import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class BerandaMvpPresenter : BaseMvpPresenter<BerandaContract.View>(), BerandaContract.Presenter {

    lateinit var postDb: DatabaseReference
    lateinit var postingList: MutableList<Posting>

    val uId = firebaseAuth.currentUser?.uid.toString()

    override fun showPosting() {
        postingList = mutableListOf()
        val userReference = AccountPresenter.userDb.child(uId)
        postDb = userReference.child("Post")
        val userRef = firebaseDatabase.getReference("User")

        uiScope.launch {

            withContext(Dispatchers.IO) {
                userRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        val errorDeferred = async { error }
                        suspend {
                            errorDeferred.let {
                                val databaseError = it.await()
                                Timber.e(databaseError.message)
                                Timber.e(databaseError.details)
                                Timber.e(databaseError.code.toString())
                                Timber.e(databaseError.toException())
                            }
                        }
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        dataSnapshot.children.forEach { snapshot ->
                            snapshot.key?.let { key ->
                                firebaseDatabase.getReference("User").child(key).child("Post")
                            }?.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    dataSnapshot.getValue(true)
                                    uiScope.launch {
                                        for (post in dataSnapshot.children) {
                                            Timber.e(post.toString())
                                            val postingValue = async { post.getValue(Posting::class.java) }
                                            postingValue.let { posting ->
                                                posting.await()?.let { postingList.add(it) }
                                                uiScope.launch {
                                                    mView?.getPosting(postingList)
                                                }
                                            }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Timber.e(error.toString())
                                }
                            })

                        }

                    }
                })
            }
        }
    }
}