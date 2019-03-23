package com.example.timebarterbeta0.ui.main.post

import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface PostContract {
    interface PostView : BaseView {
        fun showSuccesMessage()

    }

    interface PostMvpPresenter: MvpPresenter<PostView> {
        fun setPostingFirebase(posting: Posting)

//        fun createPostHelp (posting: Posting)



    }




}