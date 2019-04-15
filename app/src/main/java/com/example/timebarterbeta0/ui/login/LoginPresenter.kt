package com.example.timebarterbeta0.ui.login

import android.util.Patterns
import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginPresenter(private val mViewLogin: LoginContract.View?)
    :BaseMvpPresenter<LoginContract.View>(),
    LoginContract.Presenter {
    override fun loginAccount(account: AccountLogin) {
        uiScope.launch {
            if (validateInputlogin(account)) {
                mViewLogin?.showLoading()
                GlobalScope.launch(Dispatchers.IO) {
                    firebaseAuth.signInWithEmailAndPassword(account.email, account.password)
                        .addOnCompleteListener { task ->
                            uiScope.launch {
                                login(task)
                            }
                        }
                }
            }
        }
    }

    private fun login(login: Task<AuthResult>) {
        if (login.isSuccessful) {
            login.addOnSuccessListener { authResult ->
                //menerima authResult
                uiScope.launch {
                    mViewLogin?.let { viewLogin ->
                        Timber.e("hore mViewLogin")
                        viewLogin.loginSuccess()
                        viewLogin.hideLoading()
                    }
                }

            }
            login.addOnFailureListener {
                mViewLogin?.hideLoading()
                Timber.e(it)
            }
        } else {

            mViewLogin?.let { viewLogin ->
                viewLogin.loginFailed()
                viewLogin.hideLoading()
            }

        }
    }

    override fun validateInputlogin(account: AccountLogin): Boolean {
        val valid: Boolean

        if (Patterns.EMAIL_ADDRESS.matcher(account.email).matches()) {

            if (!account.password.isEmpty()) {
                valid = true

            } else {
                valid = false

                mViewLogin?.invalidPassword()
            }

        } else {

            valid = false
            mViewLogin?.invalidEmail()
        }

        return valid

    }
}