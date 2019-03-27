package com.example.timebarterbeta0.ui.account

import android.util.Patterns
import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import timber.log.Timber


class AccountPresenter(
    private val mViewLogin: AccountContract.ViewLogin?,
    private val mViewRegister: AccountContract.ViewRegister?,
    private val mViewAkun: AccountContract.ViewAkun?
) : BaseMvpPresenter<AccountContract.ViewRegister>(), AccountContract.AccountPresenter {

    companion object {
        const val USER_KEY = "User"
        var userDb = firebaseDatabase.getReference(USER_KEY)
    }

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
            userDb = firebaseDatabase.getReference(USER_KEY).child(uId)
            userDb.setValue(user)  //            userDb.child(NAMA_USER).setValue(user.idUser)
//            userDb.child(EMAIL).setValue(user.Email)
//            userDb.child(PASSWORD).setValue(user.Password)

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

    override fun loginAccount(account: AccountLogin) {
        uiScope.launch {
            if (validateInputlogin(account)) {
                mViewLogin?.showLoading()
                GlobalScope.launch(Dispatchers.IO) {
                    firebaseAuth.signInWithEmailAndPassword(account.email, account.password)
                        .addOnCompleteListener { login ->
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
            login.addOnSuccessListener { authResult ->
                //menerima authResult

                Timber.e("hore")
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

    override fun getUserInfo() {
        val uid = firebaseAuth.currentUser?.uid.toString()
        userDb = userDb.child(uid)
        uiScope.launch {
            withContext(Dispatchers.IO) {
                userDb.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        uiScope.launch {
                            suspend {
                                dataSnapshot.getValue(true)
                                Timber.e(dataSnapshot.toString())
                                val userDeferred: Deferred<User?> =
                                    withContext(coroutineContext) { async { dataSnapshot.getValue(User::class.java) } }
                                userDeferred.let { userInfo ->
                                    userInfo.await()?.let { mViewAkun?.showUserInfo(it) }
                                }
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Timber.e(databaseError.toString())
                    }
                })
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