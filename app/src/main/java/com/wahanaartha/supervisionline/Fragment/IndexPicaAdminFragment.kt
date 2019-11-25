package com.wahanaartha.supervisionline.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Activities.ListPicaSpvActivity
import com.wahanaartha.supervisionline.Activities.LoginActivity
import com.wahanaartha.supervisionline.Adapter.IndexPicaSpvAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_to_approve.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IndexPicaAdminFragment : Fragment() {
    var dataDetail: LoginUser? = null
    var tempDatas: ArrayList<ListApprove>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_to_approve, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Beranda"

        getData()

        swipeRefreshLayout.setOnRefreshListener { getData() }

        recycler_view_approve!!.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(context, ListPicaSpvActivity::class.java)
            intent.putExtra(ListPicaSpvActivity.TGL_SURVEY, tempDatas!![position].tglSupervisi)
            intent.putExtra(ListPicaSpvActivity.NO_DLR, tempDatas!![position].noDlr)
            intent.putExtra(ListPicaSpvActivity.ID_PARENT, tempDatas!![position].idSa)
            startActivity(intent)
        }))

    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.isRefreshing = true
        getData()
        swipeRefreshLayout.setOnRefreshListener { getData() }
    }

    fun getData() {
        val savedUser = Gson()
                .fromJson(activity
                        .getSharedPreferences(LoginActivity.MY_LOGIN_PREF, Context.MODE_PRIVATE)
                        .getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
        val id = savedUser.id
        API.getIndexPicaAdmin(id).enqueue(object : Callback<ArrayList<ListApprove>> {
            override fun onResponse(call: Call<ArrayList<ListApprove>>, response: Response<ArrayList<ListApprove>>) {
                if (response.code() == 200) {
                    tempDatas = response.body()
                    Log.i("Data Index History", "" + tempDatas)
                    if (tempDatas?.isEmpty() == true) {
                        tv_no_data.visibility = View.VISIBLE
                        recycler_view_approve.visibility = View.GONE
                    } else {
                        recycler_view_approve?.setHasFixedSize(true)
                        recycler_view_approve?.layoutManager = LinearLayoutManager(context)
                        recycler_view_approve?.adapter = IndexPicaSpvAdapter(tempDatas)
                        tv_no_data.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
                }

                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<ArrayList<ListApprove>>, t: Throwable) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false

            }
        })
    }

    companion object {
        val TGL_SURVEY = "TGL_SURVEY"
        val NO_DLR = "NO_DLR"
        val ID_SA = "ID_SA"

    }


}