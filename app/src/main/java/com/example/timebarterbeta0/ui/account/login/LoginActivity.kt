package com.example.timebarterbeta0.ui.account.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.utils.extentions.gone
import com.example.timebarterbeta0.utils.extentions.visible
import com.example.timebarterbeta0.domain.model.AccountLogin
import com.example.timebarterbeta0.ui.account.AccountContract
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.main.MainActivity
import kotlinx.android.synthetic.main.login_activity.*
import timber.log.Timber

class LoginActivity : AppCompatActivity(), AccountContract.ViewLogin {
    private lateinit var presenter: AccountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        presenter = AccountPresenter(this, null,null)

        button_login.setOnClickListener {
            Toast.makeText(this,"anu",Toast.LENGTH_LONG).show()
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
//        intent.putExtra(NAMA_USER_KEY, idUser)
//        intent.putExtra(EMAIL_USER_KEY, Email)
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

    companion object {
        val NAMA_USER_KEY = "idUser key"
        val EMAIL_USER_KEY = "Email key"
    }
}