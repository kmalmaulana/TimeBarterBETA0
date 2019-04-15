package com.example.timebarterbeta0.ui.main.akun

import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseMvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import timber.log.Timber


class AccountPresenter : BaseMvpPresenter<AccountContract.ViewAccount>(),
    AccountContract.Presenter {
    companion object {
        const val USER_REFERENCE = "User"
        var userDb = firebaseDatabase.getReference(USER_REFERENCE)
    }
    val listUser = mutableListOf<User>()
    val listUid = mutableListOf<String>()

    override fun getUserInfo() {
        userDb.keepSynced(true)
        uiScope.launch {
            getListUId()
        }
    }

    private suspend fun getListUId() {
        withContext(Dispatchers.IO) {
            userDb.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    uiScope.launch {
                        Timber.e(dataSnapshot.toString())
                        dataSnapshot.children.forEach { user ->
                            user.key?.let { userKey -> listUid.add(userKey) }
                        }
                        mView?.showUId(listUid)
                        getAllUser()
                        getUser()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Timber.e(databaseError.toString())
                }
            })
        }
    }

    private suspend fun getAllUser(){
        withContext(Dispatchers.IO){
            listUid.forEach{ uid ->
                userDb = userDb.child(uid)
                userDb.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var user: User
                        uiScope.launch {
                            val userAsync =  withContext(Dispatchers.IO){async { dataSnapshot.getValue(User::class.java) } }
                            user = userAsync.await()!!
                            listUser.add(user)
                            mView?.showAllUser(listUser)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        errorHandling(error)
                    }
                })
            }
        }
    }

    private suspend fun getUser() {
        withContext(Dispatchers.IO){
            for ((i, user) in listUser.withIndex()) {

                when (uid) {
                    listUid[i] -> mView?.showUserInfo(user)
                }
            }
        }
    }
}