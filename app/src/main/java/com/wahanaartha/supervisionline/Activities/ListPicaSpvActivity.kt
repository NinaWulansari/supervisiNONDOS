package com.wahanaartha.supervisionline.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import com.wahanaartha.supervisionline.Adapter.ListPicaSpvAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.Model.QuestionPica
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_list_pica.*
import kotlinx.android.synthetic.main.toolbar_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListPicaSpvActivity : AppCompatActivity() {

    var picaDatas: List<ListApprove>? = null
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

        tgl_pica.text = intent.getStringExtra(TGL_SURVEY)
        recyclerviewPica!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(this@ListPicaSpvActivity, ApproveActivity::class.java)
            intent.putExtra(ApproveActivity.ID, picaDatas!![position].id)
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
        val tglSurvey = intent.getStringExtra(TGL_SURVEY)
        val noDlr = intent.getStringExtra(NO_DLR)
        val idSa = intent.getStringExtra(ID_PARENT)
        API.getListPicaSpv(idSa, tglSurvey, noDlr).enqueue(object : Callback<ArrayList<ListApprove>> {
            override fun onResponse(call: Call<ArrayList<ListApprove>>, response: Response<ArrayList<ListApprove>>) {
                if (response.code() == 200) {
                    progressDialog.dismiss()
                    picaDatas = response.body()
                    Log.i("Supervisi", "kategori: " + picaDatas!!)
                    recyclerviewPica!!.setHasFixedSize(true)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recyclerviewPica!!.layoutManager = mLayoutManager
                    recyclerviewPica!!.adapter = ListPicaSpvAdapter(picaDatas)
                } else {
                    Toast.makeText(this@ListPicaSpvActivity, "Error", Toast.LENGTH_SHORT).show()
                }

                swipeRefreshLayout.isRefreshing = false

            }

            override fun onFailure(call: Call<ArrayList<ListApprove>>, t: Throwable) {
                Toast.makeText(this@ListPicaSpvActivity, "Error", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false

            }
        })
    }


    companion object {
        val TGL_SURVEY = "tgl_survey"
        val NO_DLR = "no_dlr"
        val ID_PARENT = "id_sa"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
