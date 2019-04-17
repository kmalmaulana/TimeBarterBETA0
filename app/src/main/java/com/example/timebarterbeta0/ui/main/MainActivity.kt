package com.example.timebarterbeta0.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import com.example.timebarterbeta0.LeaderBoardFragment
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.domain.model.User
import com.example.timebarterbeta0.ui.base.BaseActivity
import com.example.timebarterbeta0.ui.main.akun.AccountContract
import com.example.timebarterbeta0.ui.main.akun.AkunFragment
import com.example.timebarterbeta0.ui.main.beranda.BerandaFragment
import com.example.timebarterbeta0.ui.main.listOrder.ListFragment
import com.example.timebarterbeta0.ui.main.post.PostActivity
import com.example.timebarterbeta0.utils.extentions.listenerValueEvent
import com.example.timebarterbeta0.utils.extentions.toJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity(), AccountContract.ViewAccount{
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {
        const val EXTRA = "user"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.action_beranda -> {
                replaceFragment(BerandaFragment.newInstance(this.user.Name, listId))
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

    override fun showAllUser(listUser: MutableList<User>) {
        this.listUser = listUser
    }

    override fun showUId(listUid: MutableList<String>) {
        this.listId = listUid
    }

    override fun showUserInfo(user: User) {
        this.user = user
    }

    private var listId= mutableListOf<String>()
    private var listUser= mutableListOf<User>()
    lateinit var user: User

    override fun initLayout(): Int {
        return R.layout.main_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User()

        bottomNavigtion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(BerandaFragment.newInstance(this.user.Name,this.listId))

        fab_add_post.setOnClickListener { showPostForm() }
    }

    val db = FirebaseDatabase.getInstance()
    override fun onResume() {
        super.onResume()
        println("TES")
        val userRef = db.getReference("User")
        val postRef = db.getReference("posts")

        postRef.listenerValueEvent({
            it.toException().printStackTrace()
        }){
            it.children.forEach {
                val post = it.getValue(Posting::class.java)
                println("pos : ${post?.toJson}")
                userRef.child(post?.uId.toString()).listenerValueEvent {
                    val user = it.getValue(User::class.java)
                    Log.d("TES", "post & user : ${Pair(user, post).toJson}")
                }
            }
        }
    }

    private fun showPostForm() {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(EXTRA,user)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment)
        .commit()
    }
}