package com.example.timebarterbeta0.ui.main.post

import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.main.akun.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.example.timebarterbeta0.utils.OrderState
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostMvpPresenter : BaseMvpPresenter<PostContract.PostView>(), PostContract.PostMvpPresenter {

    companion object {
        const val POST_KEY = "Post"
        lateinit var postDb: DatabaseReference
    }
    lateinit var posting: Posting

    override fun setPostingFirebase( judul: String, deskripsi: String, jumlahWaktu: Int, posted: OrderState, currentTimeMillis: Long, category: String) {
        posting = Posting(
            firebaseAuth.currentUser?.uid,
            judul,
            deskripsi,
            jumlahWaktu,
            posted,
            currentTimeMillis,
            category,
            ""
            )
        postDb = AccountPresenter.userDb

        val postId: String = postDb.push().key.toString()
        uiScope.launch {
            withContext(Dispatchers.IO){
                postDb.child(POST_KEY).child(postId).setValue(posting).addOnSuccessListener {
                    uiScope.launch {
                        mView?.showSuccessMessage()
                    }
                }
            }
        }
    }
}
