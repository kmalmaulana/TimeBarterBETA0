/*
package com.example.timebarterbeta0.data.repository.user

import com.example.timebarterbeta0.data.firebaseHelper.FirebaseHelper
import com.example.timebarterbeta0.data.preferences.PreferenceHelper
import com.example.timebarterbeta0.model.AccountLogin
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable


class UserRepositoryImpl constructor(
    private val firebaseHelper: FirebaseHelper,
    private val preferenceHelper: PreferenceHelper
) : UserRepository {

    override fun getUser(accountLogin: AccountLogin): Observable<FirebaseUser>? {
        return firebaseHelper.userLogin(AccountLogin(accountLogin.Email, accountLogin.Password))
            ?.toObservable()
            ?.map { auth ->
                auth.user
            }?.doOnNext {
                preferenceHelper.saveUser(it.toString())
            }
    }
}

*/
