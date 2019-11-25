package com.wahanaartha.supervisionline.Adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.ListHistory
import com.wahanaartha.supervisionline.R

class IndexHistoryAdapter(internal var dataSet: List<ListHistory>?) : RecyclerView.Adapter<IndexHistoryAdapter.HistoryViewHolder>() {

    private val context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_list_history_row, parent, false)

        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val history = dataSet!![position]

        holder.question.text = history.question
        if (history.answer == null) {
            holder.status.text = "Not Answer"
            holder.relative.setBackgroundColor(Color.parseColor("#D50000"))
        } else {
            holder.status.text = "Answered"
            holder.relative.setBackgroundColor(Color.parseColor("#70c050"))

        }

    }

    override fun getItemCount(): Int {
        return if (dataSet == null) {
            0
        } else {
            dataSet!!.size
        }
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var question: TextView
        var status: TextView
        val relative: RelativeLayout

        init {
            question = itemView.findViewById<View>(R.id.text_question_history) as TextView
            status = itemView.findViewById<View>(R.id.text_status) as TextView
            relative = itemView.findViewById(R.id.relative_pica)
        }
    }
}