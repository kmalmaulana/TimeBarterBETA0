package com.example.timebarterbeta0.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.timebarterbeta0.LeaderBoardFragment
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.akun.AccountContract
import com.example.timebarterbeta0.ui.main.akun.AccountPresenter
import com.example.timebarterbeta0.ui.main.akun.AkunFragment
import com.example.timebarterbeta0.ui.main.beranda.BerandaFragment
import com.example.timebarterbeta0.ui.main.listOrder.ListFragment
import com.example.timebarterbeta0.ui.main.post.PostActivity
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : BaseActivity(), AccountContract.ViewAccount {
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var accountPresenter: AccountContract.Presenter
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.action_beranda -> {
                replaceFragment(BerandaFragment.newInstance(this.user.Name))
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_list -> {
                replaceFragment(ListFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_pesan -> {
                replaceFragment(LeaderBoardFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_akun -> {
                replaceFragment(AkunFragment.newInstance(this.user))
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun showUserInfo(user: User) {
        this.user = user
    }

    lateinit var user: User

    override fun initLayout(): Int {
        return R.layout.main_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountPresenter = AccountPresenter(this)
        accountPresenter.onAttach(this)
        user = User()
        accountPresenter.getCurrentUserInfo()

        bottomNavigtion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(BerandaFragment.newInstance(this.user.Name))

        fab_add_post.setOnClickListener { showPostForm() }
    }

    private fun showPostForm() {
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment)
        .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show()
        accountPresenter.onDetach()
    }
}