package com.example.timebarterbeta0.ui.main.post

import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostMvpPresenter : BaseMvpPresenter<PostContract.PostView>(), PostContract.PostMvpPresenter {

    companion object {
        const val POST_KEY = "Post"
        lateinit var postDb: DatabaseReference
    }
    val uId = firebaseAuth.currentUser?.uid.toString()

    override fun setPostingFirebase(posting: Posting) {

        postDb = AccountPresenter.userDb

        val uid: String = postDb.push().key.toString()
        uiScope.launch {
            withContext(Dispatchers.IO){
                postDb.child(POST_KEY).child(uid).setValue(posting).addOnSuccessListener {
                    uiScope.launch {
                        mView?.showSuccesMessage()
                    }
                }
            }
        }
    }
}
