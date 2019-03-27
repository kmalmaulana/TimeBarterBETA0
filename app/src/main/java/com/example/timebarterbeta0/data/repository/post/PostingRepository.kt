package com.example.timebarterbeta0.data.repository.post

import com.example.timebarterbeta0.domain.model.Posting
import io.reactivex.Observable

interface PostingRepository {

    fun sendPost()
    fun getPost(): Observable<Posting?>?
}
