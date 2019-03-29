package com.example.timebarterbeta0.ui.register

import android.util.Patterns
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.main.akun.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class RegisterPresenter(private val mViewRegister: RegisterContract.View?)
    : BaseMvpPresenter<RegisterContract.View>(),
    RegisterContract.Presenter {
    override fun createAccount(account: User) {
        uiScope.launch {
            if (validateInputRegister(account)) {
                mViewRegister?.showLoading()
                registerFirebase(account)
            }
        }
    }

    private fun registerFirebase(account: User) {
        account.Email?.let { nama ->
            account.Password?.let { password ->
                firebaseAuth.createUserWithEmailAndPassword(nama, password).addOnCompleteListener { task ->
                    register(task, account)
                }
            }
        }
    }

    private fun register(
        task: Task<AuthResult>,
        user: User
    ) {
        val uId: String
        if (task.isSuccessful) {
            uId = firebaseAuth.currentUser?.uid.toString()
            AccountPresenter.userDb = firebaseDatabase.getReference(AccountPresenter.USER_KEY).child(uId)
            AccountPresenter.userDb.setValue(user)

            mViewRegister?.let {
                it.registerSuccess()
                it.hideLoading()
            }

        } else {

            mViewRegister?.let {
                it.registerFailed()
                it.hideLoading()
            }

        }
    }

    override fun validateInputRegister(account: User): Boolean {

        val valid: Boolean

        when {
            account.Name?.length!! > 3 ->
                when {

                    Patterns.EMAIL_ADDRESS.matcher(account.Email).matches() ->
                        when {
                            account.Password?.length!! > 6 -> valid = true
                            else -> {
                                valid = false
                                mViewRegister?.invalidPassword()
                            }
                        }
                    else -> {
                        valid = false
                        mViewRegister?.invalidEmail()
                    }
                }
            else -> {
                valid = false
                mViewRegister?.invalidName()
            }
        }
        return valid
    }
}