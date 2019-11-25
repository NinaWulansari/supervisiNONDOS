package com.wahanaartha.supervisionline.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.ListApprove
import com.wahanaartha.supervisionline.R

class ListPicaSpvAdapter(internal var dataSet: List<ListApprove>?) : RecyclerView.Adapter<ListPicaSpvAdapter.ListPicaSpvViewHolder>() {

    private val context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPicaSpvViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_list_pica_spv_row, parent, false)

        return ListPicaSpvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListPicaSpvViewHolder, position: Int) {

        val pica = dataSet!![position]

        holder.kategori.text = pica.kategori
        holder.question.text = pica.title

        if (pica.statusPica.equals("Planned")) {
            holder.plannedImg.visibility = View.VISIBLE
            holder.status.text = "Planned"
        } else if (pica.statusPica.equals("Reschedule")) {
            holder.resImg.visibility = View.VISIBLE
            holder.status.text = "Reschedule"
        } else if (pica.statusPica.equals("Done")) {
            holder.doneImg.visibility = View.VISIBLE
            holder.status.text = "Done"
        }

    }

    override fun getItemCount(): Int {
        return if (dataSet == null) {
            0
        } else {
            dataSet!!.size
        }
    }

    inner class ListPicaSpvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var question: TextView
        var status: TextView
        var kategori: TextView
        var plannedImg: ImageView
        var resImg: ImageView
        var doneImg: ImageView
        val relative: RelativeLayout

        init {
            question = itemView.findViewById<View>(R.id.text_question) as TextView
            status = itemView.findViewById<View>(R.id.statusTextview) as TextView
            kategori = itemView.findViewById<View>(R.id.text_kategori) as TextView
            resImg = itemView.findViewById<View>(R.id.img_res) as ImageView
            doneImg = itemView.findViewById<View>(R.id.img_done) as ImageView
            plannedImg = itemView.findViewById<View>(R.id.img_plan) as ImageView
            relative = itemView.findViewById(R.id.relative_pica)


        }
    }
}