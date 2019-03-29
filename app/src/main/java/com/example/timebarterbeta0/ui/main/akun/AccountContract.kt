package com.example.timebarterbeta0.ui.main.akun

import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface  AccountContract {
    interface ViewAccount: BaseView {
        fun showUserInfo(user: User)
    }

    interface Presenter: MvpPresenter<ViewAccount> {
        fun getCurrentUserInfo()
    }
}