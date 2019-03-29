package com.example.timebarterbeta0.ui.register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.login.LoginActivity
import com.example.timebarterbeta0.utils.extentions.gone
import com.example.timebarterbeta0.utils.extentions.visible
import kotlinx.android.synthetic.main.register_activity.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        presenter = RegisterPresenter(this)
        presenter.onAttach(this)

        button_register.setOnClickListener {
            createAccount()
        }

        button_Tologin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun createAccount() {
        presenter.createAccount(
            User(
                field_nama.text.toString(),
                field_email.text.toString(),
                field_password.text.toString(),
                5,
                "",
                0,
                "0"
            )
        )
    }

    override fun showLoading() {
        progressBar.visible()

    }

    override fun registerSuccess() {
        Toast.makeText(this@RegisterActivity, "Registration success bro", Toast.LENGTH_LONG).show()
    }

    override fun registerFailed() {
        Toast.makeText(this@RegisterActivity, "Registration failed bro", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        progressBar.gone()

    }

    override fun invalidName() {
        Toast.makeText(this@RegisterActivity, "Nama terlalu pendek bro!", Toast.LENGTH_SHORT).show()
    }

    override fun invalidEmail() {
        Toast.makeText(this@RegisterActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
    }

    override fun invalidPassword() {
        Toast.makeText(this@RegisterActivity, "Password is to weak!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}


