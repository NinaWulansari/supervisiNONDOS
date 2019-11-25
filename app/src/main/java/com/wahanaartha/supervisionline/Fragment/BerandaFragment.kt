package com.wahanaartha.supervisionline.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Activities.KategoriActivity
import com.wahanaartha.supervisionline.Activities.KategoriHistoryActivity
import com.wahanaartha.supervisionline.Activities.LoginActivity
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF_KEY
import com.wahanaartha.supervisionline.Adapter.ListHistoryAdapter
import com.wahanaartha.supervisionline.Adapter.myDbAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.DealerModel
import com.wahanaartha.supervisionline.Model.HistoryIndex
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Sqlite.DatabaseHelper
import com.wahanaartha.supervisionline.Utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_hasil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class BerandaFragment : Fragment() {
    var lov_dealer: ArrayList<DealerModel?>? = null
    var dataDetail: LoginUser? = null
    var typeSupervisi: String? = null
    val namaDlr: String? = null
    var helper: myDbAdapter? = null
    var tempDatas: ArrayList<HistoryIndex>? = null
    var db: DatabaseHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_beranda, container, false)
        return rootView
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Beranda"
        val spinnerArray = ArrayList<String>()
        val date = getCurrentDateTime()
        val tgl_supervisi = date.toString("yyyy/MM/dd")

        helper = myDbAdapter(activity)
        helper!!.deleteKategori()

        var savedUser = Gson()
                .fromJson(activity?.getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)?.getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

        if(savedUser.groupId.equals("1")){
            spinnerArray.add("DOS")
        } else{
            spinnerArray.add("NON DOS")
        }

        val adapter = ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipe!!.adapter = adapter
        spinnerTipe!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                typeSupervisi = spinnerTipe!!.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        getNamaUser()
        getDealer({})

        start.setOnClickListener {
            val intent = Intent(activity, KategoriActivity::class.java)
            val get_dealer = (spinnerDealer.selectedItem as? DealerModel)?.noDlr
            val get_namaDlr = (spinnerDealer.selectedItem as? DealerModel)?.nmDlr
            val get_type_dealer = (spinnerDealer.selectedItem as? DealerModel)?.typeDlr

            db = DatabaseHelper(activity)
            db!!.deleteNote()

            if (get_dealer == null) {
                Toast.makeText(activity, "Please select dealer", Toast.LENGTH_LONG).show()
            } else {
                val prefs = activity.getSharedPreferences("X", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("type_supervisi", typeSupervisi)
                editor.putString("no_dlr", get_dealer)
                editor.putString("tgl_supervisi", tgl_supervisi)
                editor.putString("type_dealer", get_type_dealer)
                editor.putString("nama_dealer", get_namaDlr)
                editor.commit()

                startActivity(intent)
            }

        }
    }

    fun getNamaUser() {
        val savedUser = Gson()
                .fromJson(activity
                        .getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)
                        .getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
        val id_user = savedUser.id
        API.getUser(id_user).enqueue(object : Callback<LoginUser> {
            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                dataDetail = response.body()
                nama_user!!.text = dataDetail!!.name
            }
            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Toast.makeText(activity, "Error get name", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getDealer(onFinish: () -> Unit){
        val savedUser = Gson()
                .fromJson(activity?.getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)?.getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

        val id = savedUser.id
        val group_user = savedUser.groupId
        API.getLovDealer(id, group_user).enqueue(object : retrofit2.Callback<ArrayList<DealerModel>> {
            override fun onResponse(call: Call<ArrayList<DealerModel>>, response: Response<ArrayList<DealerModel>>) {
                if (response.code() == 200) {
                    lov_dealer = ArrayList()
                    lov_dealer?.add(0, null)
                    response.body()?.forEach { lov_dealer?.add(it) }
                    val adapter = CustomAdapter<DealerModel?>(activity, R.layout.spinner_custom, R.layout.spinner_dropdown_item, lov_dealer?.toTypedArray()!!)
                    spinnerDealer.adapter = adapter
                    onFinish()
                }
            }

            override fun onFailure(call: Call<ArrayList<DealerModel>>, throwable: Throwable) {
                Toast.makeText(activity, "Error get dealer", Toast.LENGTH_LONG).show()
            }
        })
    }

    class CustomAdapter<T>(context: FragmentActivity?, val viewResourceId: Int, val dropDownReourceId: Int, val list: Array<T>) : ArrayAdapter<T>(context, viewResourceId, dropDownReourceId, list) {

        internal var layoutInflater: LayoutInflater = context!!.layoutInflater

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent, dropDownReourceId)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent, viewResourceId)
        }

        fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?, resourceId: Int): View {

            var view = convertView

            if (view == null) {
                view = layoutInflater.inflate(resourceId, parent, false)
            }

            val textView = view as? TextView
            if (list[position] != null) {
                textView?.text = list[position].toString()
            } else {
                textView?.text = "Choose a Dealer"
            }

            return view!!
        }

        override fun isEnabled(position: Int): Boolean {
            return position != 0
        }
    }

}