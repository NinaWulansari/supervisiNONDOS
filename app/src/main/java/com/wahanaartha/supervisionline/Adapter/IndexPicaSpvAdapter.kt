package com.wahanaartha.supervisionline.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class IndexPicaSpvAdapter(internal var dataSet: ArrayList<ListApprove>?) : RecyclerView.Adapter<IndexPicaSpvAdapter.IndexPicaSpvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexPicaSpvViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_index_pica_spv_row, parent, false)

        return IndexPicaSpvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IndexPicaSpvViewHolder, position: Int) {
        val approve = dataSet!![position]

        holder.name.text = approve.name
        holder.dealer.text = approve.nmDlr

        val date = approve.tglSupervisi
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        try {
            val dap = inputFormat.parse(date)
            val outputText = outputFormat.format(dap)
            holder.tgl.text = outputText
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return dataSet!!.size
    }

    inner class IndexPicaSpvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var tgl: TextView
        var dealer: TextView

        init {
            name = itemView.findViewById<View>(R.id.tv_sa_name) as TextView
            tgl = itemView.findViewById<View>(R.id.tv_tgl) as TextView
            dealer = itemView.findViewById<View>(R.id.tv_dealer) as TextView

        }
    }
}
