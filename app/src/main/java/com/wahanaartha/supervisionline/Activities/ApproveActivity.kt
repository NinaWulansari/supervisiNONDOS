package com.wahanaartha.supervisionline.Activities

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import butterknife.ButterKnife
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Connect.API.baseURLPicasso
import com.wahanaartha.supervisionline.Model.AddPica
import com.wahanaartha.supervisionline.Model.Approve
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.activity_approve.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ApproveActivity : AppCompatActivity() {

    var approveModel: Approve? = null
    internal var myCalendar = Calendar.getInstance()

    var statusPica = arrayOf("Planned", "Reschedule", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        titleSearch!!.text = "Approve"
        titleSearch!!.setTextColor(Color.WHITE)

        val id = this.intent.getStringExtra(ID)

        getData(id)
        validation()

        button_save_approve.setOnClickListener {
//            updateData()
        }

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDeadline()
        }

        val newDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelNewDeadline()
        }

        ed_deadline.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@ApproveActivity, R.style.DatePickerDialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        ed_new_deadline.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@ApproveActivity, R.style.DatePickerDialogTheme, newDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val adapterStatus = ArrayAdapter(this@ApproveActivity, android.R.layout.simple_spinner_item, statusPica)
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_status_pica_approve!!.adapter = adapterStatus

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

    fun getData(id: String) {
        API.getApprove(id).enqueue(object : Callback<Approve> {
            override fun onResponse(call: Call<Approve>, response: Response<Approve>) {
                if (response.code() == 200) {
                    approveModel = response.body()
                    kategori.text = approveModel?.kategori
                    header.text = approveModel?.titleHeader
                    ed_notes.setText(approveModel?.notes)
                    ed_reason.setText(approveModel?.reason)
                    ed_deadline.setText(approveModel?.deadline)
                    ed_new_deadline.setText(approveModel?.newDeadline)
                    Picasso.with(this@ApproveActivity)
                            .load(baseURLPicasso + approveModel?.pic)
                            .into(im_approve)

//                    if (approveModel?.statusApprove.equals("1")) {
                        ed_deadline.isEnabled = false
                        ed_new_deadline.isEnabled = false
                        button_save_approve.isEnabled = false
                        button_save_approve.isClickable = false
                        button_save_approve.setBackgroundColor(Color.parseColor("#979594"))
//                    }

                    if (approveModel?.statusPica.equals("Planned")) {
                        sp_status_pica_approve.setSelection((sp_status_pica_approve.adapter as ArrayAdapter<String>).getPosition("Planned"))
                    } else if (approveModel?.statusPica.equals("Done")) {
                        sp_status_pica_approve.setSelection((sp_status_pica_approve.adapter as ArrayAdapter<String>).getPosition("Done"))
                    } else if (approveModel?.statusPica.equals("Reschedule")) {
                        sp_status_pica_approve.setSelection((sp_status_pica_approve.adapter as ArrayAdapter<String>).getPosition("Reschedule"))
                    }

                    r1c1_app.isSelected = false
                    r1c1_app.isClickable = false

                    r2c1_app.isSelected = false
                    r2c1_app.isClickable = false

                    r1c2_app.isSelected = false
                    r1c2_app.isClickable = false

                    r2c2_app.isSelected = false
                    r2c2_app.isClickable = false

                    if (approveModel?.remark.equals("1")) {
                        r1c1_app.isChecked = true
                    } else if (approveModel?.remark.equals("2")) {
                        r2c1_app.isChecked = true
                    } else if (approveModel?.remark.equals("3")) {
                        r1c2_app.isChecked = true
                    } else if (approveModel?.remark.equals("4")) {
                        r2c2_app.isChecked = true
                    }

                    if (approveModel?.pi.isNullOrEmpty()) {
                        ed_pi_app?.setText("")
                    } else {
                        ed_pi_app?.setText(approveModel?.pi!!)
                    }
                } else {
                    Toast.makeText(this@ApproveActivity, "Error", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<Approve>, t: Throwable) {
                Toast.makeText(this@ApproveActivity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validation() {

        ed_pi_app.isEnabled = false
        ed_ca_app.isEnabled = false
        sp_status_pica_approve.isClickable = false
        sp_status_pica_approve.isEnabled = false
        ed_reason.isEnabled = false

        sp_status_pica_approve.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if (sp_status_pica_approve.selectedItem.equals("Reschedule")) {
                    ed_deadline.isEnabled = false
                    new_deadline.visibility = View.VISIBLE
                    reason.visibility = View.VISIBLE
                    notes.visibility = View.GONE
                } else if (sp_status_pica_approve.selectedItem.equals(("Planned"))) {
                    new_deadline.visibility = View.GONE
                    reason.visibility = View.GONE
                    notes.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

    }

//    fun updateData() {
//        val progressDialog = ProgressDialog(this@ApproveActivity)
//        progressDialog.setMessage("Updating...")
//        progressDialog.show()
//
//        android.os.Handler().postDelayed({
//
//            val savedUser = Gson()
//                    .fromJson(this@ApproveActivity
//                            .getSharedPreferences(LoginActivity.MY_LOGIN_PREF, Context.MODE_PRIVATE)
//                            .getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
//
//            val deadline = ed_deadline.text.toString()
//            val new_deadline = ed_new_deadline.text.toString()
//            val modi_by = savedUser.id
//
//            val id = this.intent.getStringExtra(ID)
//
//            API.editApprove(id, deadline, new_deadline, modi_by).enqueue(object : Callback<AddPica> {
//                override fun onResponse(call: Call<AddPica>, response: Response<AddPica>) {
//                    if (response.code() == 200) {
//                        progressDialog.dismiss()
//                        Toast.makeText(this@ApproveActivity, "Succes", Toast.LENGTH_SHORT).show()
//                        //                        startActivity(Intent(activity, CustomerIndexActivity::class.java))
//
//                        this@ApproveActivity.finish()
//                    } else {
//                        Toast.makeText(this@ApproveActivity, "Failed", Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//
//                override fun onFailure(call: Call<AddPica>, t: Throwable) {
//                    Toast.makeText(this@ApproveActivity, "Error", Toast.LENGTH_SHORT).show()
//                }
//            })
//            finish()
//            progressDialog.dismiss()
//        }, 2000)
//    }


    companion object {
        val ID = "id"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }


}