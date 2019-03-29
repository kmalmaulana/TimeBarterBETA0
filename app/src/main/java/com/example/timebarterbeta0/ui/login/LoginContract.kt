package com.example.timebarterbeta0.ui.login

import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface LoginContract {
    interface View: BaseView{
        fun loginSuccess()
        fun loginFailed()
        fun invalidPassword()
        fun invalidEmail()

    }

    interface Presenter:MvpPresenter<View> {

        fun loginAccount(account: AccountLogin)
        fun validateInputlogin(account: AccountLogin): Boolean
    }
}