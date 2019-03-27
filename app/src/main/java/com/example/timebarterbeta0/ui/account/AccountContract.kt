package com.example.timebarterbeta0.ui.account

import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseView
import com.example.timebarterbeta0.ui.base.MvpPresenter

interface  AccountContract {

    interface ViewRegister: BaseView {
        fun registerSuccess()
        fun registerFailed()
        fun invalidName()
        fun invalidEmail()
        fun invalidPassword()
    }

    interface ViewLogin: BaseView {
        fun loginSuccess()
        fun loginFailed()
        fun invalidEmail()
        fun invalidPassword()

    }



    interface AccountPresenter: MvpPresenter<ViewRegister> {

        fun loginAccount (account: AccountLogin)
        fun validateInputRegister(account: User): Boolean
        fun validateInputlogin(account: AccountLogin): Boolean
        fun createAccount(account: User)
        fun getUserInfo()
    }

    interface ViewAkun {
        fun showUserInfo(user: User)
    }
}