package com.example.timebarterbeta0.ui.base

import android.support.v4.app.DialogFragment
import com.example.timebarterbeta0.utils.extentions.gone
import com.example.timebarterbeta0.utils.extentions.visible
import kotlinx.android.synthetic.main.register_activity.*

open class BaseDialogFragment: DialogFragment(),BaseView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }
}
