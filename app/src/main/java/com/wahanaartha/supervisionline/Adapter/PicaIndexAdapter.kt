package com.wahanaartha.supervisionline.Adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.QuestionPica
import com.wahanaartha.supervisionline.R

class PicaIndexAdapter(internal var dataSet: List<QuestionPica>?) : RecyclerView.Adapter<PicaIndexAdapter.PicaViewHolder>() {

    private val context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_list_pica_row, parent, false)

        return PicaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PicaViewHolder, position: Int) {

        val pica = dataSet!![position]

        holder.question.text = pica.titleHeader
        holder.kategori.text = pica.kategori
        if (pica.statusApprove == "0") {
            holder.status.text = "Pending"
            holder.relative.setBackgroundColor(Color.parseColor("#428bca"))
            holder.status.setTextColor(Color.parseColor("#428bca"))

        } else if (pica.statusApprove == "1") {
            if (pica.statusPica.equals("Done")) {
                holder.status.text = "Done"
                holder.relative.setBackgroundColor(Color.parseColor("#70c050"))
                holder.status.setTextColor(Color.parseColor("#70c050"))
            } else {
                holder.status.text = "Approve"
                holder.relative.setBackgroundColor(Color.parseColor("#70c050"))
                holder.status.setTextColor(Color.parseColor("#70c050"))
            }
        } else if (pica.statusApprove == "2") {
            holder.status.text = "Reject"
            holder.relative.setBackgroundColor(Color.parseColor("#D50000"))
            holder.status.setTextColor(Color.parseColor("#D50000"))
        }

        if (pica.statusPica.equals("Planned")) {
            holder.plannedImg.visibility = View.VISIBLE
        } else if (pica.statusPica.equals("Reschedule")) {
            holder.resImg.visibility = View.VISIBLE
        } else if (pica.statusPica.equals("Done")) {
            holder.doneImg.visibility = View.VISIBLE
        } else if (pica.statusPica.equals("0")){
            holder.plannedImg.visibility = View.GONE
            holder.resImg.visibility = View.GONE
            holder.doneImg.visibility = View.GONE

        }

    }

    override fun getItemCount(): Int {
        return if (dataSet == null) {
            0
        } else {
            dataSet!!.size
        }
    }

    inner class PicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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