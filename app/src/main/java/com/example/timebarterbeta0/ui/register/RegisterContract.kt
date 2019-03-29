package com.example.timebarterbeta0.ui.register

import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface RegisterContract {
    interface View: BaseView{
        fun registerSuccess()
        fun registerFailed()
        fun invalidName()
        fun invalidEmail()
        fun invalidPassword()
    }

    interface Presenter:MvpPresenter<View> {
        fun createAccount(account: User)
        fun validateInputRegister(account: User): Boolean
    }
}