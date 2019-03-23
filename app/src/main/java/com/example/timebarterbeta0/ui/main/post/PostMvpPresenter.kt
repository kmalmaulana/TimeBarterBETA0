package com.example.timebarterbeta0.ui.main.post

import android.widget.Toast
import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DatabaseReference


class PostMvpPresenter : BaseMvpPresenter<PostContract.PostView>(), PostContract.PostMvpPresenter {

    companion object {
        const val POST_KEY = "Post"
        lateinit var postDb: DatabaseReference
    }
    val uId = firebaseAuth.currentUser?.uid.toString()

    override fun setPostingFirebase(posting: Posting) {

        postDb = AccountPresenter.userDb.child(uId)

        val uid: String = postDb.push().key.toString()

        posting.let { post ->
            postDb.child(POST_KEY).child(uid).setValue(post).addOnSuccessListener {
                mView?.showSuccesMessage()
            }
        }
    }
}
