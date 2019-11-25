package com.wahanaartha.supervisionline.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wahanaartha.supervisionline.Model.KategoriModel
import com.wahanaartha.supervisionline.Model.Valid
import com.wahanaartha.supervisionline.R

class KategoriAdapter(internal var dataSet: List<KategoriModel>?) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {
    var sqlite: myDbAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_kategori, parent, false)
        sqlite = myDbAdapter(parent.context)

        return KategoriViewHolder(itemView)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val kategori = dataSet!![position]
        val ok = dataSet!![0].id
        Log.d("ok", "$ok")
        Log.d("sqlite", "" + sqlite!!.allCategories)

        if(kategori.answered){
            holder.itemView.setClickable(false)
            holder.itemView.setEnabled(false)
            holder.card_kategori.setCardBackgroundColor(Color.parseColor("#EF9A9A"))
        }else{
            holder.itemView.setClickable(true)
            holder.itemView.setEnabled(true)
        }

        holder.kategori_soal.text = kategori.kategori
    }

    override fun getItemCount(): Int {
        return if (dataSet == null) {
            0
        } else {
            dataSet!!.size
        }
    }

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var kategori_soal: TextView
        var card_kategori: CardView

        init {
            kategori_soal = itemView.findViewById<View>(R.id.kategori_soal) as TextView
            card_kategori = itemView.findViewById<View>(R.id.card_kategori) as CardView
        }
    }
}
