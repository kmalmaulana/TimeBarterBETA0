package com.example.timebarterbeta0.data.repository.post

//import com.example.timebarterbeta0.data.firebaseHelper.FirebaseHelper
import com.example.timebarterbeta0.data.preferences.PreferenceHelper
import com.example.timebarterbeta0.domain.Posting
import io.reactivex.Observable


/*
class PostingRepositoryImpl(private val firebaseHelper: FirebaseHelper,
                            private val preferenceHelper: PreferenceHelper) : PostingRepository{
    override fun sendPost(){

    }

    override fun getPost(): Observable<Posting?>? {
        return firebaseHelper.getPosting()
            ?.toObservable()
            ?.doOnNext{
                preferenceHelper.posting = it.toString()
            }?.doOnSubscribe {

            }?.doOnComplete {
                preferenceHelper.posting
            }
    }
}
*/
