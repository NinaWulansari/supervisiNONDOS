package com.wahanaartha.supervisionline.Activities

import android.app.AlertDialog
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
import com.wahanaartha.supervisionline.Adapter.KategoriAdapter
import com.wahanaartha.supervisionline.Adapter.myDbAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.KategoriModel
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Sqlite.DatabaseHelper
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_kategori.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Fragment.BerandaFragment


class KategoriActivity : AppCompatActivity() {

    internal var tipe: String? = null
    internal var namaDlr: String? = null
    var kategoriDatas: ArrayList<KategoriModel> = ArrayList()
    private var toolbar: Toolbar? = null
    var db: DatabaseHelper? = null
    var helper: myDbAdapter? = null
    lateinit var adapter: KategoriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "Kategori"
        titleSearch!!.setTextColor(Color.WHITE)
        val dlr = intent.getStringExtra(KategoriActivity.NO_DLR)
        val type_dlr = intent.getStringExtra(KategoriActivity.TYPE_DLR)
        namaDlr = intent.getStringExtra(KategoriActivity.NAMA_DLR)

        val prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
        tipe = prefs.getString("type_supervisi", "")

        db = DatabaseHelper(this)

        helper = myDbAdapter(this)

        done.setBackgroundColor(Color.parseColor("#EF9A9A"))
        not_done.visibility = View.GONE
        tv_not_done.visibility = View.GONE

        getData()

        recycler_question!!.addOnItemTouchListener(RecyclerItemClickListener(this, RecyclerItemClickListener.OnItemClickListener { view, position ->
            if(!kategoriDatas[position].answered){
                val intent = Intent(this@KategoriActivity, QuizActivity::class.java)
                val editor = prefs.edit()

                editor.putString("id_kategori", kategoriDatas[position].id)
                editor.putString("nama_kategori", kategoriDatas[position].kategori)
                editor.commit()
                intent.putExtra(QuizActivity.TYPE_DLR, type_dlr)
                intent.putExtra(QuizActivity.NO_DLR, dlr)
                intent.putExtra(QuizActivity.NAMA_DLR, namaDlr)
                Log.i("supervisi", "nina: " + kategoriDatas[position].id)
                startActivity(intent)
            }
        }))
    }

    private fun getData() {
        val progressDialog: ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.progress = ProgressDialog.STYLE_SPINNER
        progressDialog.setMessage("Loading..")
        progressDialog.show()
        API.getKategori(tipe).enqueue(object : Callback<ArrayList<KategoriModel>> {
            override fun onResponse(call: Call<ArrayList<KategoriModel>>, response: Response<ArrayList<KategoriModel>>) {
                if (response.code() == 200) {
                    progressDialog.dismiss()
                    kategoriDatas = response.body()!!
                    adapter = KategoriAdapter(kategoriDatas)
                    val edit = "oke"
                    Log.d("Supervisi", "kategori: $kategoriDatas")
                    recycler_question!!.setHasFixedSize(true)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recycler_question!!.layoutManager = mLayoutManager
                    recycler_question!!.adapter = adapter

                    //ALERT DIALOG SUPERVISI DONE (ALL KATEGORI ANSWERED)
//                    if (db!!.notesCount == kategoriDatas.size) {
//                        val dialog = android.support.v7.app.AlertDialog.Builder(this@KategoriActivity)
//                        dialog.setMessage("Anda sudah menyelesaikan supervisi ini.")
//                                .setPositiveButton("OK", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
//                                    finish()
//                                })
//                        dialog.show()
//                    }

                    adapter.notifyDataSetChanged()
                    setDisable()
                } else {
                    Toast.makeText(this@KategoriActivity, "Connection Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<KategoriModel>>, t: Throwable) {
                Toast.makeText(this@KategoriActivity, "Connection Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setDisable() {
        val data = helper!!.allCategories
        Log.d("dataS", "$data")

        for (i in 0 until data!!.size) {
            for (j in 0 until kategoriDatas.size) {
                Log.d("equals", data[i] + kategoriDatas[j].id)
                if (kategoriDatas[j].id.equals(data[i])){
                    kategoriDatas[j].answered = true
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        if(tipe == "NON DOS"){
            val prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("lastActivity", javaClass.name)
            editor.putString("type_supervisi", tipe)
            editor.commit()
        }
    }

    companion object {
        val TYPE = "type"
        val NO_DLR = "no_dlr"
        val NAMA_DLR = "nama_dlr"
        val TYPE_DLR = "type_dlr"

    }

    override fun onBackPressed() {
//        Toast.makeText(this@KategoriActivity, "count : "+db!!.notesCount + kategoriDatas.size, Toast.LENGTH_SHORT).show()

        if (tipe.equals("NON DOS")) {
            if (db!!.notesCount == kategoriDatas.size) {
                super.onBackPressed()

            } else {
                val dialog = android.support.v7.app.AlertDialog.Builder(this@KategoriActivity)
                dialog.setMessage("You Must Finish All The Category")
                dialog.setPositiveButton("OK", null)
                dialog.show()
            }
        } else {
            super.onBackPressed()
            finish()
        }
    }
}
