package com.wahanaartha.supervisionline.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wahanaartha.supervisionline.Adapter.myDbAdapter
import com.wahanaartha.supervisionline.Message
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Sqlite.DatabaseHelper
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.toolbar.*


class ResultActivity : AppCompatActivity() {

    var db: DatabaseHelper? = null
    lateinit var parent_id: String
    var helper: myDbAdapter? = null
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "Result"
        titleSearch!!.setTextColor(Color.WHITE)
        db = DatabaseHelper(this)
        prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
        parent_id = prefs.getString("id_kategori", "")

        button_save.setOnClickListener({
            createNote("1")
            addUser()
            finish()
            val intent = Intent(this@ResultActivity, KategoriActivity::class.java)
            startActivity(intent)
        })
        helper = myDbAdapter(this)
    }

    fun addUser() {
        if (parent_id.isEmpty()) {
            Message.message(applicationContext, "Enter Both Name and Password")
        } else {
            val id = helper!!.insertData(parent_id)
            if (id <= 0) {
                Log.d("status_insert", "insert sqlite failed")
            } else {
                Log.d("status_insert", "insert sqlite success")
            }
        }
    }

    private fun createNote(note: String) {
        val id = db!!.insertNote(note)
        val n = db?.getNote(id)

    }

    companion object {
        val MAIN_ITEM = "main_item"
    }
}

