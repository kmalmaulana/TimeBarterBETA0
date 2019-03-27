package com.example.timebarterbeta0.ui.main.beranda


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timebarterbeta0.R
import com.example.timebarterbeta0.domain.model.Posting
import com.example.timebarterbeta0.ui.base.BaseFragment
import com.example.timebarterbeta0.ui.main.beranda.detail.DetailPostActivity
import kotlinx.android.synthetic.main.fragment_beranda.*


class BerandaFragment : BaseFragment(), BerandaContract.View{

    val presenter : BerandaContract.Presenter

    companion object {
        val POSTING_EXTRA = "posting"
        fun newInstance(): BerandaFragment {
            val args = Bundle()
            val fragment = BerandaFragment()
            fragment.arguments = args
            return fragment
        }
    }

    init {
        presenter = BerandaMvpPresenter()
    }

    override fun getPosting(posting: MutableList<Posting>?) {
        rv_list_post.layoutManager= LinearLayoutManager(context)
        rv_list_post.adapter = posting?.let {
            BerandaAdapter(it,listener = { posting ->
                val intent = Intent(activity, DetailPostActivity::class.java)
                intent.putExtra(POSTING_EXTRA,posting)
                startActivity(intent)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.showPosting()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
    }

    override fun onStop()  {
        super.onStop()
        presenter.onDetach()
    }

}
