package com.wahanaartha.supervisionline.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import com.wahanaartha.supervisionline.Activities.ApproveActivity.Companion.ID
import com.wahanaartha.supervisionline.Adapter.ListApproveDetailAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_list_approve.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListApprovedActivity : AppCompatActivity() {

    var tempDatas: ArrayList<ListApprove>? = null

    var selectAll: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_approve)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "Approve"
        titleSearch!!.setTextColor(Color.WHITE)

//        getData()
//
//        swipeRefreshLayout.setOnRefreshListener { getData() }
//
//        recyclerviewApprove!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
//            val intent = Intent(this, ApproveActivity::class.java)
//            intent.putExtra(ID, tempDatas!![position].id)
//            startActivity(intent)
//        }))

    }

//    fun getData() {
//        val created_by = intent.getStringExtra(ID_SA)
//        val tgl_supervisi = intent.getStringExtra(TGL_SURVEY)
//        val no_dlr = intent.getStringExtra(NO_DLR)
//
//        API.getListApprovedDetail(created_by, tgl_supervisi, no_dlr).enqueue(object : Callback<ArrayList<ListApprove>> {
//            override fun onResponse(call: Call<ArrayList<ListApprove>>, response: Response<ArrayList<ListApprove>>) {
//                if (response.code() == 200) {
//                    tempDatas = response.body()
//                    Log.i("Data Index History", "" + tempDatas)
//                    recyclerviewApprove?.setHasFixedSize(true)
//                    recyclerviewApprove?.layoutManager = LinearLayoutManager(this@ListApprovedActivity)
//                    val adapter = ListApproveDetailAdapter(tempDatas)
//                    recyclerviewApprove?.adapter = adapter
//                } else {
//                    Toast.makeText(this@ListApprovedActivity, "Error", Toast.LENGTH_LONG).show()
//                }
//
//                swipeRefreshLayout.isRefreshing = false
//
//            }
//
//            override fun onFailure(call: Call<ArrayList<ListApprove>>, t: Throwable) {
//                Toast.makeText(this@ListApprovedActivity, "Error", Toast.LENGTH_SHORT).show()
//
//                swipeRefreshLayout.isRefreshing = false
//
//            }
//        })
//    }


    companion object {
        val TGL_SURVEY = "tgl_survey"
        val NO_DLR = "no_dlr"
        val ID_SA = "id_sa"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}