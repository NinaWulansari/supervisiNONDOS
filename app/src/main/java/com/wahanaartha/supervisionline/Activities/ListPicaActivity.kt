package com.wahanaartha.supervisionline.Activities

import android.app.ProgressDialog
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
import com.wahanaartha.supervisionline.Adapter.PicaIndexAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.QuestionPica
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_list_pica.*
import kotlinx.android.synthetic.main.toolbar_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListPicaActivity : AppCompatActivity() {

    var picaDatas: List<QuestionPica>? = null
    var datas: List<QuestionPica>? = null
    private var toolbar: Toolbar? = null
    private var searchActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pica)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "PICA"
        titleSearch!!.setTextColor(Color.WHITE)

        swipeRefreshLayout.setOnRefreshListener { getData() }

        getData()

        val tgl = intent.getStringExtra(TGL_SURVEY)
        val dlr = intent.getStringExtra(NO_DLR)
        tgl_pica.text = intent.getStringExtra(TGL_SURVEY)
        recyclerviewPica!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(this@ListPicaActivity, PicaActivity::class.java)
            intent.putExtra(PicaActivity.ID_PARENT, picaDatas!![position].id)
            intent.putExtra(PicaActivity.TGL_SURVEY, tgl)
            intent.putExtra(PicaActivity.NO_DLR, dlr)
            Log.i("supervisi", "ratri: " + picaDatas!![position].id)
            startActivity(intent)
        }))

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
        API.getListPica(tgl, no_dlr).enqueue(object : Callback<ArrayList<QuestionPica>> {
            override fun onResponse(call: Call<ArrayList<QuestionPica>>, response: Response<ArrayList<QuestionPica>>) {
                if (response.code() == 200) {
                    progressDialog.dismiss()
                    picaDatas = response.body()
                    if(picaDatas!!.size != 0){
                        Log.i("Supervisi", "kategori: " + picaDatas!!)
                        recyclerviewPica!!.setHasFixedSize(true)
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclerviewPica!!.layoutManager = mLayoutManager
                        recyclerviewPica!!.adapter = PicaIndexAdapter(picaDatas)
                    }else{
                        tv_no_data.visibility = View.VISIBLE
                        recyclerviewPica.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@ListPicaActivity, "Error", Toast.LENGTH_SHORT).show()
                }

                swipeRefreshLayout.isRefreshing = false

            }

            override fun onFailure(call: Call<ArrayList<QuestionPica>>, t: Throwable) {
                Toast.makeText(this@ListPicaActivity, "Error", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false

            }
        })
    }


    companion object {
        val TGL_SURVEY = "tgl_survey"
        val NO_DLR = "no_dlr"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
