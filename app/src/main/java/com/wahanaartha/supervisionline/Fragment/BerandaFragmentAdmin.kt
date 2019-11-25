package com.wahanaartha.supervisionline.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Activities.LoginActivity
import com.wahanaartha.supervisionline.Adapter.CardAdminAdapter
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.Model.Model
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.fragment_beranda.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BerandaFragmentAdmin : Fragment() {
    var dataDetail: LoginUser? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_beranda_admin, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity.title = "Beranda"
        val list = ArrayList<Model>()
        list.add(Model(Model.DONE, "", 0))
        list.add(Model(Model.NOT_DONE, "", 0))

        val adapterCard = CardAdminAdapter(list, activity)
        val linearLayoutManager = LinearLayoutManager(activity, OrientationHelper.HORIZONTAL, false)
        val mRecyclerView = view!!.findViewById<View>(R.id.rvCard) as RecyclerView
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = adapterCard

        getNamaUser()
    }

    fun getNamaUser() {
        val savedUser = Gson()
                .fromJson(activity
                        .getSharedPreferences(LoginActivity.MY_LOGIN_PREF, Context.MODE_PRIVATE)
                        .getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
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
}