package com.example.timebarterbeta0.ui.main.beranda

import android.util.Log
import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.example.timebarterbeta0.utils.extentions.listenerValueEvent
import com.example.timebarterbeta0.utils.extentions.toJson
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class BerandaMvpPresenter : BaseMvpPresenter<BerandaContract.View>(), BerandaContract.Presenter {

    var postingList = mutableListOf<Posting>()
    private var post: Posting? = null

    override fun showPosting(listUId: List<String>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {

                firebaseDatabase.getReference("posts")
                    .listenerValueEvent({ it.toException().printStackTrace() })
                    {
                        it.children.forEach { dataSnapshot ->
                            post = dataSnapshot.getValue(Posting::class.java)
                            println("pos : ${post?.toJson}")
                            firebaseDatabase.getReference("User").child(post?.uId.toString()).listenerValueEvent {
                                val user = it.getValue(User::class.java)
                                Timber.d("post & user : ${Pair(user, post).toJson}")
                                post?.let { posting ->
                                    postingList.add(posting)
                                }
                                mView?.getPosting(postingList)
                            }
                        }
                    }
            }
        }
    }
}
