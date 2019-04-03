package com.example.timebarterbeta0.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.MainActivity
import com.example.timebarterbeta0.utils.extentions.gone
import com.example.timebarterbeta0.utils.extentions.visible
import kotlinx.android.synthetic.main.login_activity.*
import timber.log.Timber

class LoginActivity : BaseActivity(), LoginContract.View {
    private lateinit var presenter: LoginContract.Presenter

    override fun initLayout(): Int {
        return R.layout.login_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(this)

        presenter.onAttach(this)

        button_login.setOnClickListener {
//            Toast.makeText(this,"anu",Toast.LENGTH_LONG).show()
            presenter.loginAccount(
                AccountLogin(
                    field_login_email.text.toString(),
                    field_login_password.text.toString()
                )
            )
            Timber.e(field_login_email.text.toString())
            Timber.e(field_login_password.text.toString())
        }
    }

    override fun showLoading() {
        progressBar_login.visible()
    }

    override fun loginSuccess() {
        Toast.makeText(this@LoginActivity, "Selamat Datang!", Toast.LENGTH_LONG).show()

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginFailed() {
        Toast.makeText(this@LoginActivity, "Email dan Password salah!", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        progressBar_login.gone()
    }

    override fun invalidEmail() {
        Toast.makeText(this@LoginActivity, "Tolong masukkan Email dengan benar!", Toast.LENGTH_SHORT).show()
    }

    override fun invalidPassword() {
        Toast.makeText(this@LoginActivity, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
    }
}