package com.example.timebarterbeta0.ui.main.beranda

import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.*
import timber.log.Timber

class BerandaMvpPresenter : BaseMvpPresenter<BerandaContract.View>(), BerandaContract.Presenter{

    lateinit var postDb: DatabaseReference
    lateinit var postingList: MutableList<Posting>

    val uId = firebaseAuth.currentUser?.uid.toString()

    override fun showPosting() {

        postingList = mutableListOf()
        val userReference = AccountPresenter.userDb.child(uId)
        postDb = userReference.child("Post")
        val userRef = firebaseDatabase.getReference("User")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Timber.e(error.message)
                Timber.e(error.details)
                Timber.e(error.code.toString())
                Timber.e(error.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { snapshot ->
                    snapshot.key?.let { key ->
                        firebaseDatabase.getReference("User").child(key).child("Post")
                    }?.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.getValue(true)
                            for (post in dataSnapshot.children) {
                                Timber.e(post.toString())
                                val postingValue = post.getValue(Posting::class.java)

                                postingValue?.let { posting ->

                                    postingList.add(posting)
                                    mView?.getPosting(postingList)

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