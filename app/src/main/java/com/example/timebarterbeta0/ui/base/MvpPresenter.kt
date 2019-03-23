package com.example.timebarterbeta0.ui.base

interface MvpPresenter<in V: BaseView> {

    fun onAttach(view: V)
    fun onDetach()
}
