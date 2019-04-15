package com.example.timebarterbeta0.ui.main.beranda

import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class BerandaMvpPresenter : BaseMvpPresenter<BerandaContract.View>(), BerandaContract.Presenter {

//    lateinit var postDb: DatabaseReference
    var postingList: MutableList<Posting>? = null

    override fun showPosting(listUId: List<String>) {
        postingList = mutableListOf()
//        val userReference = AccountPresenter.userDb.child(uid)
//        postDb = userReference.child("Post")
//        postDb.keepSynced(true)
        uiScope.launch {
            listUId.forEach { listUId ->
                firebaseDatabase.getReference("User").child(listUId).child("Post")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.getValue(true)
                            uiScope.launch {
                                for (post in dataSnapshot.children) {
                                    Timber.e(post.toString())
                                    val postingValue = async { post.getValue(Posting::class.java) }
                                    postingValue.let { posting ->
                                        posting.await()?.let { postingList?.add(it) }
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
    }
}
