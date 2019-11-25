package com.wahanaartha.supervisionline.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Activities.LoginActivity
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.fragment_beranda_spv.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BerandaFragmentSpv : Fragment() {
    var dataDetail: LoginUser? = null
    var tempDatas: ArrayList<ListApprove>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_beranda_spv, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Beranda"

        getNamaUser()


        setupViewPager(viewpager_approve)

        tabs_approve.setupWithViewPager(viewpager_approve)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(fragmentManager)
        adapter.addFrag(ToApprovedFragment(), "To Approve")
        adapter.addFrag(ApprovedFragment(), "Approved")
        tabs_approve.tabTextColors = ContextCompat.getColorStateList(context, R.color.white)
        tabs_approve.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.white))
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
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
                tv_name_spv!!.text = dataDetail!!.name
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Toast.makeText(activity, "Error get name", Toast.LENGTH_LONG).show()

            }
        })
    }

}