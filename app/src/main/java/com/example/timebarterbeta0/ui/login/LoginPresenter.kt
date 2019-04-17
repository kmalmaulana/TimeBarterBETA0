package com.example.timebarterbeta0.ui.login

import android.util.Patterns
import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.*
import timber.log.Timber

class LoginPresenter(private val mViewLogin: LoginContract.View?) : BaseMvpPresenter<LoginContract.View>(),
    LoginContract.Presenter {
    override fun loginAccount(account: AccountLogin) {
        uiScope.launch {
            mViewLogin?.showLoading()
            if (validateInputlogin(account)) {
                Timber.e("anu berhasil")
                withContext(Dispatchers.IO) {
                    firebaseAuth.signInWithEmailAndPassword(account.email, account.password)
                        .addOnCompleteListener { login ->
                            Timber.e("login....")
                            uiScope.launch {
                                login(login)
                            }
                        }
                }
            }
        }
    }

    private fun login(login: Task<AuthResult>) {

        if (login.isSuccessful) {
            Timber.e("hore")
            login.addOnSuccessListener {
                //menerima authResult
                mViewLogin?.let { viewLogin ->
                    Timber.e("hore mViewLogin")
                    viewLogin.loginSuccess()
                    viewLogin.hideLoading()
                }
            }
            login.addOnFailureListener {
                mViewLogin?.hideLoading()
                Timber.e(it)
                mViewLogin?.loginFailed()
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