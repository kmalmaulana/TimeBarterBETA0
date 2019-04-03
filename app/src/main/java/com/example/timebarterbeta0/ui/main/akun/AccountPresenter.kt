package com.example.timebarterbeta0.ui.main.akun

import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Logger
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import timber.log.Timber


class AccountPresenter(
    private val mViewAkun: AccountContract.ViewAccount?
) : BaseMvpPresenter<AccountContract.ViewAccount>(),
    AccountContract.Presenter {

    companion object {
        const val USER_KEY = "User"
        var userDb = firebaseDatabase.getReference(USER_KEY)
    }

    val listUser = mutableListOf<String>()

    override fun getCurrentUserInfo() {
//        val uid = firebaseAuth.currentUser?.uid.toString()
//        userDb = userDb.child(uid)
        userDb.keepSynced(true)
        uiScope.launch {
            withContext(Dispatchers.IO) {
                userDb.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        uiScope.launch {
                            Timber.e(dataSnapshot.toString())
                            dataSnapshot.children.forEach { user ->
                                user.key?.let { listUser.add(it) }
                            }
//                            dataSnapshot.getValue(true)
                            showUser(dataSnapshot)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Timber.e(databaseError.toString())
                    }
                })
            }
        }
    }

    private suspend fun showUser(dataSnapshot: DataSnapshot) {

        val userDeferred=
            withContext(coroutineContext) { async { dataSnapshot.getValue(User::class.java) } }

        withContext(coroutineContext){
            userDeferred.let { userInfo ->
                userInfo.await()?.let { user ->
                    mViewAkun?.showUserInfo(user)
                }
            }
        }
    }
}