package com.example.timebarterbeta0.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.timebarterbeta0.LeaderBoardFragment
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.account.AccountContract
import com.example.timebarterbeta0.ui.account.AccountPresenter
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.akun.AkunFragment
import com.example.timebarterbeta0.ui.main.beranda.BerandaFragment
import com.example.timebarterbeta0.ui.main.post.PostActivity
import com.example.timebarterbeta0.ui.main.listOrder.ListFragment
import com.example.timebarterbeta0.ui.main.post.PostContract
import com.example.timebarterbeta0.ui.main.post.PostMvpPresenter
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : BaseActivity(), AccountContract.ViewAkun {

    private var presenter: PostContract.PostMvpPresenter
    private var accountPresenter: AccountContract.AccountPresenter

    init {
        presenter = PostMvpPresenter()
        accountPresenter = AccountPresenter(null,null,this)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.action_beranda -> {
                replaceFragment(BerandaFragment.newInstance())
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
                replaceFragment(AkunFragment.newInstance(user))
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

        user = User()

        accountPresenter.getUserInfo()

        bottomNavigtion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(BerandaFragment.newInstance())

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
}