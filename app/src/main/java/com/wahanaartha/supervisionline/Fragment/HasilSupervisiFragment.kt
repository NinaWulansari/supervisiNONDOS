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
import com.wahanaartha.supervisionline.Activities.ListPicaActivity
import com.wahanaartha.supervisionline.Activities.LoginActivity
import com.wahanaartha.supervisionline.Adapter.ListHistoryAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.HistoryIndex
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_hasil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HasilSupervisiFragment : Fragment() {
    var tempDatas: ArrayList<HistoryIndex>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_hasil, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.title = "HASIL SUPERVISI"
        getData()

        swipeRefreshLayout.setOnRefreshListener { getData() }

        recyclerviewHasil!!.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(context, ListPicaActivity::class.java)
            intent.putExtra(ListPicaActivity.Companion.TGL_SURVEY, tempDatas!![position].tglSupervisi)
            intent.putExtra(ListPicaActivity.Companion.NO_DLR, tempDatas!![position].noDlr)
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
        val created_by = savedUser.id

        API.getHistoryIndex(created_by).enqueue(object : Callback<ArrayList<HistoryIndex>> {
            override fun onResponse(call: Call<ArrayList<HistoryIndex>>, response: Response<ArrayList<HistoryIndex>>) {
                if (response.code() == 200) {
                    tempDatas = response.body()
                    Log.i("Data Index History", "" + tempDatas)
                    if (tempDatas == null) {
                        tv_no_data.visibility = View.VISIBLE
                    } else {
                        tv_no_data.visibility = View.GONE
                        recyclerviewHasil?.setHasFixedSize(true)
                        recyclerviewHasil?.layoutManager = LinearLayoutManager(context)
                        recyclerviewHasil?.adapter = ListHistoryAdapter(tempDatas)
                    }

                } else {
                    Toast.makeText(activity, "Gagal", Toast.LENGTH_LONG).show()
                }
                swipeRefreshLayout.isRefreshing = false

            }

            override fun onFailure(call: Call<ArrayList<HistoryIndex>>, t: Throwable) {
                Toast.makeText(activity, "gagal", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false

            }
        })
    }
}