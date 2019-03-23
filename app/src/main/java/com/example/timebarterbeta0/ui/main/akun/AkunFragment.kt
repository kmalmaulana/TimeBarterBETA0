package com.example.timebarterbeta0.ui.main.akun


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.User
import com.example.timebarterbeta0.ui.account.login.LoginActivity
import com.example.timebarterbeta0.utils.extentions.loadImage
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.fragment_akun.*

class AkunFragment : Fragment(){

    companion object {
        val USER = "user"
        fun newInstance(user: User): AkunFragment {

            val bundle = Bundle()
            bundle.putParcelable(USER,user)
            val fragment = AkunFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    var user: User? = User()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_akun, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val userInfo : String
        arguments?.let {
            val idUser = it.getString("idUser")
            val Email = it.getString("Email")
            //Me
//            var judul:String? = it.getString("judul")
//            var deskripsi:String? = it.getString("description")

            Timber.e(idUser)
            Timber.e(Email)

            tv_name.text = idUser
            tv_email.text = Email*/
            //this
//            et_judul.text = judul
//        }

        user = arguments?.getParcelable(USER)
        showUserInfo(user)
        button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()

            Toast.makeText(context, getString(R.string.logout_toast), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showUserInfo(user: User?) {
        if (user != null) {
            tv_name.text = user.Name
            iv_profile_picture.loadImage(user.photoProfile.toString())
            tv_email.text = user.Email
            tv_points.text = user.point.toString()
            tv_level.text = user.level
        }

    }
}
