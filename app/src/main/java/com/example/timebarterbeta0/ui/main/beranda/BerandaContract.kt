package com.example.timebarterbeta0.ui.main.beranda

import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface BerandaContract {
    interface Presenter: MvpPresenter<View> {
        fun showPosting(listUId: List<String>)
    }
    interface View: BaseView{
        fun getPosting(posting: MutableList<Posting>?)
    }
}
