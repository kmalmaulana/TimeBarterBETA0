package com.example.timebarterbeta0


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class LeaderBoardFragment : Fragment() {

    companion object {
        fun newInstance(): LeaderBoardFragment {
            val args = Bundle()
            val fragment = LeaderBoardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesan, container, false)
    }


}
