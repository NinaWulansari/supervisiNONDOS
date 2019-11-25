package com.wahanaartha.supervisionline.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.wahanaartha.supervisionline.Adapter.KategoriAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.KategoriModel
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_kategori.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class KategoriHistoryActivity : AppCompatActivity() {

    var kategoriDatas: ArrayList<KategoriModel> = ArrayList()
    private var toolbar: Toolbar? = null
    lateinit var adapter: KategoriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "Kategori"
        titleSearch!!.setTextColor(Color.WHITE)
        getData()

        val dlr = intent.getStringExtra(NO_DLR)
        val type = intent.getStringExtra(TYPE)
        val tgl = intent.getStringExtra(TGL_SURVEY)
        val nama_dlr = intent.getStringExtra(NAMA_DLR)
        for (j in 0 until kategoriDatas!!.size) {
            kategoriDatas!![j].answered = true
        }

        tv_keterangan.visibility = View.GONE

        recycler_question!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
            val intent = Intent(this@KategoriHistoryActivity, ListHistoryActivity::class.java)
            intent.putExtra(ListHistoryActivity.Companion.ID_PARENT, kategoriDatas[position].id)
            intent.putExtra(ListHistoryActivity.Companion.NAME, kategoriDatas[position].kategori)
            intent.putExtra(ListHistoryActivity.TGL_SURVEY, tgl)
            intent.putExtra(ListHistoryActivity.TYPE, type)
            intent.putExtra(ListHistoryActivity.NO_DLR, dlr)
            intent.putExtra(ListHistoryActivity.NAMA_DLR, nama_dlr)
            startActivity(intent)
        }))
    }

    private fun getData() {
        val progressDialog: ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.progress = ProgressDialog.STYLE_SPINNER
        progressDialog.setMessage("Loading..")
        progressDialog.show()
        val type = intent.getStringExtra(TYPE)

        API.getKategori(type).enqueue(object : Callback<ArrayList<KategoriModel>> {
            override fun onResponse(call: Call<ArrayList<KategoriModel>>, response: Response<ArrayList<KategoriModel>>) {
                if (response.code() == 200) {
                    progressDialog.dismiss()
                    kategoriDatas = response.body()!!
                    adapter = KategoriAdapter(kategoriDatas)

                    Log.i("Supervisi", "kategori: " + kategoriDatas)
                    recycler_question!!.setHasFixedSize(true)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recycler_question!!.layoutManager = mLayoutManager
                    recycler_question!!.adapter = adapter
                    adapter.notifyDataSetChanged()
                    setDisable()

                } else {
                    Toast.makeText(this@KategoriHistoryActivity, "Connection Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<KategoriModel>>, t: Throwable) {
                Toast.makeText(this@KategoriHistoryActivity, "Connection Error1", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDisable() {
        val prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
        val kategoriAnswered = prefs.getString("answered", "")

        Log.d("dataS", "$kategoriAnswered")

//        if (kategoriDatas.id.equals(data[i])){
//            kategoriDatas[j].answered = true
//        }

        adapter.notifyDataSetChanged()
    }

    companion object {
        val TGL_SURVEY = "tgl_survey"
        val NO_DLR = "no_dlr"
        val TYPE = "type"
        val NAMA_DLR = "nama"
    }

}
