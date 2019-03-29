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

    lateinit var presenter : BerandaContract.Presenter

    companion object {
        const val POSTING_EXTRA = "posting"
        const val USER_EXTRA = "username"
        fun newInstance(userName: String?): BerandaFragment {
            val args = Bundle()
            args.putString(USER_EXTRA,userName)
            val fragment = BerandaFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var userName: String? = ""

    init {

    }

    override fun getPosting(posting: MutableList<Posting>?) {
        rv_list_post.layoutManager= LinearLayoutManager(context)
        rv_list_post.adapter = posting?.let { listPosting ->
            BerandaAdapter(
                listPosting = listPosting,
                listener = cardViewListener(),
                userName = userName
            )
        }
    }

    private fun cardViewListener(): (Posting) -> Unit {
        return { posting: Posting ->
            val intent = Intent(activity, DetailPostActivity::class.java)
            showDetail(intent, posting)
            presenter.showDetail {
                showDetail(intent, posting)
            }
        }
    }

    private fun showDetail(intent: Intent, posting: Posting) {
        intent.putExtra(POSTING_EXTRA, posting)
        startActivity(intent)
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
        presenter = BerandaMvpPresenter()
        presenter.onAttach(this)
        presenter.showPosting()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = arguments?.getString(USER_EXTRA)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

}
