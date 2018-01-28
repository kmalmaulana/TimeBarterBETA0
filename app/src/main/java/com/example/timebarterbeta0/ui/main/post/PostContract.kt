package com.example.timebarterbeta0.ui.main.post

import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter
import com.example.timebarterbeta0.utils.OrderState

interface PostContract {
    interface PostView : BaseView {
        fun showSuccessMessage()

    }

    interface PostMvpPresenter: MvpPresenter<PostView> {
        fun setPostingFirebase(
            judul: String,
            deskripsi: String,
            jumlahWaktu: Int,
            posted: OrderState,
            currentTimeMillis: Long,
            category: String
        )

//        fun createPostHelp (posting: Posting)



    }




}