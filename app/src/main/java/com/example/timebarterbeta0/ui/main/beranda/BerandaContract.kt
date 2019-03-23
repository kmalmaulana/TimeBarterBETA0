package com.example.timebarterbeta0.ui.main.beranda

import com.example.timebarterbeta0.domain.Posting
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface BerandaContract {
    interface Presenter: MvpPresenter<View> {

        fun showPosting()
    }
    interface View: BaseView{
        fun getPosting(posting: MutableList<Posting>?)
    }
}
