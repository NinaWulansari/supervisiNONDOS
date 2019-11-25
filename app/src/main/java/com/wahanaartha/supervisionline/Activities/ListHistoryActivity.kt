package com.wahanaartha.supervisionline.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.wahanaartha.supervisionline.Adapter.IndexHistoryAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.ListHistory
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_list_history.*
import kotlinx.android.synthetic.main.toolbar_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListHistoryActivity : AppCompatActivity() {

    var historyDatas: List<ListHistory>? = null
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_history)
        val tgl = intent.getStringExtra(TGL_SURVEY)
        val dlr = intent.getStringExtra(NO_DLR)
        val name = intent.getStringExtra(NAME)
        val id_parent = intent.getStringExtra(ID_PARENT)
        val type = intent.getStringExtra(TYPE)
        val nama_dlr = intent.getStringExtra(NAMA_DLR)
        setSupportActionBar(toolbar)

        titleSearch!!.text = "$name"
        titleSearch!!.setTextColor(Color.WHITE)

        swipeRefreshLayout.setOnRefreshListener { getData() }

        getData()

        recyclerviewHistory!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(this@ListHistoryActivity, HistoryActivity::class.java)
            intent.putExtra(HistoryActivity.NAME, name)
            intent.putExtra(HistoryActivity.ID_PARENT, id_parent)
            intent.putExtra(HistoryActivity.ID_ITEM, historyDatas!![position].id_question)
            intent.putExtra(HistoryActivity.TITLE, historyDatas!![position].question)
            intent.putExtra(HistoryActivity.TYPE, type)
            intent.putExtra(HistoryActivity.LAST_ROOT, historyDatas!![position].lastRoot)
            intent.putExtra(HistoryActivity.EXIST_GOOD, historyDatas!![position].existGood)
            intent.putExtra(HistoryActivity.EXIST_NOT_GOOD, historyDatas!![position].existNotGood)
            intent.putExtra(HistoryActivity.NOT_EXIST, historyDatas!![position].notExist)
            intent.putExtra(HistoryActivity.N_A, historyDatas!![position].na)
            intent.putExtra(HistoryActivity.PI, historyDatas!![position].pi)
            intent.putExtra(HistoryActivity.CA, historyDatas!![position].ca)
            intent.putExtra(HistoryActivity.PIC, historyDatas!![position].pic)
            intent.putExtra(HistoryActivity.NEW_DEADLINE, historyDatas!![position].newDeadline)
            intent.putExtra(HistoryActivity.DEADLINE, historyDatas!![position].deadline)
            intent.putExtra(HistoryActivity.REASON, historyDatas!![position].reason)
            intent.putExtra(HistoryActivity.NOTES, historyDatas!![position].pi)
            intent.putExtra(HistoryActivity.ANSWER, historyDatas!![position].answer)
            intent.putExtra(HistoryActivity.TGL_SURVEY, tgl)
            intent.putExtra(HistoryActivity.NO_DLR, dlr)
            intent.putExtra(HistoryActivity.NAMA_DLR, nama_dlr)

            startActivity(intent)
        }))

//        val arrayTrue = java.util.ArrayList<String?>()
//        for (i in 0 until historyDatas!!.size) {
//            val dataAnswered = historyDatas!![i].answer
//            if(historyDatas!![i].answer != null){
//                arrayTrue.add(dataAnswered.toString())
//            }
//        }
//
//        Log.d("TANDA", "$arrayTrue")
//        Log.d("teslog", "nina")

//        if(historyDatas!!.size == arrayTrue.size){
//            val dialog = android.support.v7.app.AlertDialog.Builder(this@ListHistoryActivity)
//            dialog.setMessage("Anda sudah menyelesaikan supervisi ini.")
//                    .setPositiveButton("OK", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
//                        val prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
//                        val editor = prefs.edit()
//                        editor.putBoolean("answered", true)
//                        editor.commit()
//                        finish()
//                    })
//            dialog.show()
//        }
    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.isRefreshing = true
        getData()
        swipeRefreshLayout.setOnRefreshListener { getData() }
    }

    private fun getData() {
        val progressDialog: ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.progress = ProgressDialog.STYLE_SPINNER
        progressDialog.setMessage("Loading..")
        progressDialog.show()
        val tgl = intent.getStringExtra(TGL_SURVEY)
        val no_dlr = intent.getStringExtra(NO_DLR)
        val id_parent = intent.getStringExtra(ID_PARENT)
        val type = intent.getStringExtra(TYPE)

        API.getIndexHistory(id_parent, type, tgl, no_dlr).enqueue(object : Callback<ArrayList<ListHistory>> {
            override fun onResponse(call: Call<ArrayList<ListHistory>>, response: Response<ArrayList<ListHistory>>) {
                if (response.code() == 200) {
                    progressDialog.dismiss()
                    historyDatas = response.body()
//                    Log.i("Supervisi", "kategori: " + historyDatas!!)
                    if (historyDatas == null) {
                        tv_no_data.visibility = View.VISIBLE
                    } else {
                        tv_no_data.visibility = View.GONE
                        recyclerviewHistory!!.setHasFixedSize(true)
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclerviewHistory!!.layoutManager = mLayoutManager
                        recyclerviewHistory!!.adapter = IndexHistoryAdapter(historyDatas)

                    }

                } else {
                    Toast.makeText(this@ListHistoryActivity, "Error", Toast.LENGTH_SHORT).show()
                }

                swipeRefreshLayout.isRefreshing = false

            }

            override fun onFailure(call: Call<ArrayList<ListHistory>>, t: Throwable) {
                Toast.makeText(this@ListHistoryActivity, "Error", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false

            }
        })
    }


    companion object {
        val TGL_SURVEY = "tgl_survey"
        val NO_DLR = "no_dlr"
        val TYPE = "type"
        val ID_PARENT = "id_parent"
        val NAME = "name"
        val NAMA_DLR = "nm_dlr"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
