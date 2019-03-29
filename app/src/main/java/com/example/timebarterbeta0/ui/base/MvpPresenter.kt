package com.example.timebarterbeta0.ui.base

interface MvpPresenter<V: BaseView> {

    fun onAttach(view: V)
    fun onDetach()
}
