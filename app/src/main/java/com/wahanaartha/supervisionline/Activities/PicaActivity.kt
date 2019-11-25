package com.wahanaartha.supervisionline.Activities

//import com.wahanaartha.supervisionline.Connect.API.baseURLPicasso
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF_KEY
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Connect.API.baseURLPicasso
import com.wahanaartha.supervisionline.Model.AddPica
import com.wahanaartha.supervisionline.Model.Detail
import com.wahanaartha.supervisionline.Model.GetDetailPica
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.activity_add_pica.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ratri on 10/23/2018.
 */

class PicaActivity : AppCompatActivity() {

    var questionsModel: GetDetailPica? = null
    var dataSet : List<Detail>? = null

    internal var myCalendar = Calendar.getInstance()

    var statusPica = arrayOf("Planned", "Reschedule", "Done")

    //    ArrayList<>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pica)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "PICA"
        titleSearch!!.setTextColor(Color.WHITE)

        val id = this.intent.getStringExtra(ID_PARENT)

        getData(id)

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDeadline()
        }

        ed_deadline.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@PicaActivity, R.style.DatePickerDialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val newDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelNewDeadline()
        }

        ed_new_deadline.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@PicaActivity, R.style.DatePickerDialogTheme, newDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val adapterStatus = ArrayAdapter(this@PicaActivity, android.R.layout.simple_spinner_item, statusPica)
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_status_pica!!.adapter = adapterStatus

        button_save_pica.setOnClickListener {
            updateData()
        }
        validation()

//        im_pica.setOnClickListener(){
//            val intent = Intent(this, ShowImageDetailFragment::class.java)
//            intent.putExtra(IMAGE, baseURLPicasso+questionsModel?.pic)
//            startActivity(intent)
//        }

    }

    fun getData(id: String) {
        API.getPica(id).enqueue(object : Callback<GetDetailPica> {
            override fun onResponse(call: Call<GetDetailPica>, response: Response<GetDetailPica>) {
                if (response.code() == 200) {
                    questionsModel = response.body()
                    kategori.text = questionsModel?.kategori
                    header_pica.text = questionsModel?.titleHeader
                    ed_notes.setText(questionsModel?.notes)
                    ed_reason.setText(questionsModel?.reason)
                    ed_deadline.setText(questionsModel?.deadline)
                    ed_new_deadline.setText(questionsModel?.newDeadline)

                    recycler_view_pica!!.setHasFixedSize(true)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recycler_view_pica!!.layoutManager = mLayoutManager
                    recycler_view_pica!!.adapter = DetailAdapter(dataSet)
                    Picasso.with(this@PicaActivity)
                            .load(baseURLPicasso + questionsModel?.pic)
                            .into(im_pica)

                    if (questionsModel?.statusPica.equals("Planned")) {
                        sp_status_pica.setSelection((sp_status_pica.adapter as ArrayAdapter<String>).getPosition("Planned"))
                    } else if (questionsModel?.statusPica.equals("Done")) {
                        sp_status_pica.setSelection((sp_status_pica.adapter as ArrayAdapter<String>).getPosition("Done"))
                    } else if (questionsModel?.statusPica.equals("Reschedule")) {
                        sp_status_pica.setSelection((sp_status_pica.adapter as ArrayAdapter<String>).getPosition("Reschedule"))
                    }

                    r1c1_pica.isSelected = false
                    r1c1_pica.isClickable = false

                    r2c1_pica.isSelected = false
                    r2c1_pica.isClickable = false

                    r1c2_pica.isSelected = false
                    r1c2_pica.isClickable = false

                    r2c2_pica.isSelected = false
                    r2c2_pica.isClickable = false

                    if (questionsModel?.remark.equals("1")) {
                        r1c1_pica.isChecked = true
                    } else if (questionsModel?.remark.equals("2")) {
                        r2c1_pica.isChecked = true
                    } else if (questionsModel?.remark.equals("3")) {
                        r1c2_pica.isChecked = true
                    } else if (questionsModel?.remark.equals("4")) {
                        r2c2_pica.isChecked = true
                    }

                    if (questionsModel?.statusPica.equals("Done")) {
                        button_save_pica.isEnabled = false
                        button_save_pica.isClickable = false
                        button_save_pica.setBackgroundColor(resources.getColor(R.color.grey_400))
                        ed_notes.isEnabled = false
                        ed_ca.isEnabled = false
                        sp_status_pica.isClickable = false
                        sp_status_pica.isEnabled = false
                        ed_deadline.isEnabled = false
                    } else if (questionsModel?.statusApprove.equals("1")){
                        ed_ca.isEnabled = false
                        ed_deadline.isEnabled = false
                    }

                    if (questionsModel?.ca.isNullOrEmpty()) {

                        ed_ca?.setText("")
                        sp_status_pica.isEnabled = false
                        sp_status_pica.isClickable = false

                    } else if (questionsModel?.ca != null) {

                        ed_ca?.setText(questionsModel?.ca!!)
                    }


                    if (questionsModel?.pi.isNullOrEmpty()) {
                        ed_pi?.setText("")
                    } else {
                        ed_pi?.setText(questionsModel?.pi!!)
                    }
                } else {
                    Toast.makeText(this@PicaActivity, "Error", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<GetDetailPica>, t: Throwable) {
                Toast.makeText(this@PicaActivity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun updateData() {
        val progressDialog = ProgressDialog(this@PicaActivity)
        progressDialog.setMessage("Updating...")
        progressDialog.show()

        android.os.Handler().postDelayed({

            val savedUser = Gson()
                    .fromJson(this@PicaActivity
                            .getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)
                            .getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

            val ca = ed_ca.text.toString()
            val reason = ed_reason.text.toString()
            val notes = ed_notes.text.toString()
            val deadline = ed_deadline.text.toString()
            var new_deadline = ed_new_deadline.text.toString()
            val status_pica = sp_status_pica.selectedItem.toString()
            val modi_by = savedUser.id

            val id = this.intent.getStringExtra(ID_PARENT)

            if(sp_status_pica.equals("Planned")){
                new_deadline = "0000-00-00"
            }

            API.editPica(id, status_pica, notes, reason, ca, deadline, new_deadline, modi_by).enqueue(object : Callback<AddPica> {
                override fun onResponse(call: Call<AddPica>, response: Response<AddPica>) {
                    if (response.code() == 200) {
                        progressDialog.dismiss()
                        Toast.makeText(this@PicaActivity, "Succes", Toast.LENGTH_SHORT).show()

                        finish()
                    } else {
                        Toast.makeText(this@PicaActivity, "Failed", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<AddPica>, t: Throwable) {
                    Toast.makeText(this@PicaActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
            finish()
            progressDialog.dismiss()
        }, 2000)
    }

    fun validation() {

        ed_pi.isEnabled = false

        sp_status_pica.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if (sp_status_pica.selectedItem.equals("Reschedule")) {
                    new_deadline.visibility = View.VISIBLE
                    reason.visibility = View.VISIBLE
                    notes.visibility = View.GONE
                } else if (sp_status_pica.selectedItem.equals("Done")) {
                    new_deadline.visibility = View.GONE
                    reason.visibility = View.GONE
                    notes.visibility = View.VISIBLE
                } else {
                    new_deadline.visibility = View.GONE
                    reason.visibility = View.GONE
                    notes.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

    }

    private fun updateLabelDeadline() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        ed_deadline.setText(sdf.format(myCalendar.time))
    }

    private fun updateLabelNewDeadline() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        ed_new_deadline.setText(sdf.format(myCalendar.time))
    }

    class DetailAdapter(internal var dataSet: List<Detail>?) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_question_pica_row, parent, false)

            return DetailViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            val detail = dataSet!![position]

            holder.detail_pica.text = detail.title
        }

        override fun getItemCount(): Int {
            return if (dataSet == null) {
                0
            } else {
                dataSet!!.size
            }
        }

        inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var detail_pica: TextView

            init {
                detail_pica = itemView.findViewById<View>(R.id.detail_pica) as TextView
            }
        }
    }

    companion object {
        val TGL_SURVEY = "tgl_survey"
        val ID_PARENT = "id"
        val NO_DLR = "no_dlr"
        val IMAGE = "image"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
