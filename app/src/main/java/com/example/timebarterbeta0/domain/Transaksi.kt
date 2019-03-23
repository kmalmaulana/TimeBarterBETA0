package com.example.timebarterbeta0.domain

class Transaksi {
    var user: List<User>
    var posting : Posting?
    var dateTransaction: Long

    constructor(){}

    constructor(user: List<User>, posting: Posting, dateTransaction: Long) {
        this.user = user
        this.posting = posting
        this.dateTransaction = dateTransaction
    }

    init {
        user = listOf()
        posting = null
        dateTransaction = 0
    }
}