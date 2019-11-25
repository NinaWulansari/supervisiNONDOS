package com.wahanaartha.supervisionline.Adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ListApproveDetailAdapter(internal var dataSet: ArrayList<ListApprove>?) : RecyclerView.Adapter<ListApproveDetailAdapter.ListApproveDeatilViewHolder>() {

    var isSelectedAll = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListApproveDeatilViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_list_approve_row, parent, false)

        return ListApproveDeatilViewHolder(itemView)
    }

    private lateinit var mSelectedItemsIds: SparseBooleanArray

    fun selectAll() {
        Log.e("onClickSelectAll", "yes")
        Log.e("datasize",""+dataSet)
        isSelectedAll = true
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListApproveDeatilViewHolder, position: Int) {
        val approve = dataSet!![position]

        holder.soal.text = approve.title
        holder.kategori.text = approve.kategori

        val date = approve.tglSupervisi
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        try {
            val dap = inputFormat.parse(date)
            val outputText = outputFormat.format(dap)
            holder.tgl.text = outputText
        } catch (e: ParseException) {
            e.printStackTrace()
        }

//        if (approve.statusPica.equals("Planned")) {
//            holder.im_planned.visibility = View.VISIBLE
//
//        } else if (approve.statusPica.equals("Reschedule")) {
//            holder.im_reschedule.visibility = View.VISIBLE
//        }

        if (!isSelectedAll){
            holder.checkBox.setChecked(false)
        } else {
            holder.checkBox.setChecked(true)
        }
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    inner class ListApproveDeatilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var kategori: TextView
        var soal: TextView
        var tgl: TextView
        val im_planned: ImageView
        val im_reschedule: ImageView
        val checkBox: CheckBox


        init {
            kategori = itemView.findViewById<View>(R.id.text_kategori_app) as TextView
            tgl = itemView.findViewById<View>(R.id.tglSupervisi) as TextView
            soal = itemView.findViewById<View>(R.id.text_question_app) as TextView
            im_planned = itemView.findViewById<View>(R.id.img_plan) as ImageView
            im_reschedule = itemView.findViewById<View>(R.id.img_res) as ImageView
            checkBox = itemView.findViewById(R.id.chk_selected) as CheckBox

        }
    }

    public fun checkCheckBox(position: Int, value: Boolean) {
        if (value)
            mSelectedItemsIds?.put(position, true)
        else
            mSelectedItemsIds?.delete(position)

        notifyDataSetChanged()
    }


}


