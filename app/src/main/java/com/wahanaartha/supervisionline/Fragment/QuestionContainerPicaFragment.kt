package com.wahanaartha.supervisionline.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.wahanaartha.supervisionline.Model.QuestionPica
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.activity_add_pica.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ratri on 10/23/2018.
 */


class QuestionContainerPicaFragment : Fragment() {
    internal var questionPica: QuestionPica? = null
    internal var myCalendar = Calendar.getInstance()


    var statusPica = arrayOf("Planned", "Reschedule", "Done")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_add_pica, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onViewCreated(view, savedInstanceState)
        header_pica.text = questionPica!!.titleHeader
        r1c1_pica.isSelected = false
        r1c1_pica.isClickable = false

        r2c1_pica.isSelected = false
        r2c1_pica.isClickable = false

        r1c2_pica.isSelected = false
        r1c2_pica.isClickable = false

        r2c2_pica.isSelected = false
        r2c2_pica.isClickable = false

        if (questionPica!!.remark.equals("1")) {
            r1c1_pica.isChecked = true
        } else if (questionPica!!.remark.equals("2")) {
            r2c1_pica.isChecked = true
        } else if (questionPica!!.remark.equals("3")) {
            r1c2_pica.isChecked = true
        } else if (questionPica!!.remark.equals("4")) {
            r2c2_pica.isChecked = true
        }

        if (questionPica?.ca.isNullOrEmpty()) {
            ed_ca?.setText("")
        } else if (questionPica?.ca != null) {

            ed_ca?.setText(questionPica?.ca!!)
        }

        if (questionPica?.pi.isNullOrEmpty()) {
            ed_pi?.setText("")
        } else {
            ed_pi?.setText(questionPica?.pi!!)
        }

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDeadline()
        }

        ed_deadline.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this.context, R.style.DatePickerDialogTheme, date, myCalendar
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
            DatePickerDialog(this.context, R.style.DatePickerDialogTheme, newDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val adapterStatus = ArrayAdapter(context, android.R.layout.simple_spinner_item, statusPica)
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_status_pica!!.adapter = adapterStatus

        validation()

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

        if (questionPica?.ca == null) {
            sp_status_pica.isEnabled = false
            sp_status_pica.isClickable = false

        }
    }


    companion object {

        fun getInstance(questionPica: QuestionPica): QuestionContainerPicaFragment {
            val fragment = QuestionContainerPicaFragment()
            fragment.questionPica = questionPica
            return fragment
        }
    }
}