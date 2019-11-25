package com.wahanaartha.supervisionline.Activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.Model.Notif
import com.wahanaartha.supervisionline.R
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 101)

        }

//        Handler().postDelayed({
//            val savedUser = Gson().fromJson(getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE).getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
//
//            if (savedUser != null && savedUser.status == "1" && savedUser.loginDateTime != null) {
//                val loginDateTime = savedUser.loginDateTime
//                val now = Calendar.getInstance()
//                val loginCalendar = Calendar.getInstance()
//                loginCalendar.time = loginDateTime
//                loginCalendar.add(Calendar.HOUR, -6)
//
//                if (loginCalendar.before(now)) {
//                    startActivity(Intent(this@LoginActivity, NavigationDrawer::class.java))
//                    finish()
//                }
//                else {
//                    val i = Intent(this@LoginActivity, LoginActivity::class.java)
//                    startActivity(i)
//                    finish()
//                }
//
//            }
//            else {
//                val i = Intent(this@LoginActivity, LoginActivity::class.java)
//                startActivity(i)
//                finish()
//            }
//        }, TIME_OUT.toLong())
//

        btn_login.setOnClickListener {
            btn_login.visibility = View.INVISIBLE
            progressBar.visibility= View.VISIBLE
            val username = input_username.text.toString()
            val password = input_password.text.toString()

            if (username.equals("") || password.equals("")) {
                Toast.makeText(this, "Username & password Must Be Filled", Toast.LENGTH_SHORT).show()
                btn_login.visibility = View.VISIBLE
                progressBar.visibility= View.INVISIBLE
            }

            API.login(username, password).enqueue(object : retrofit2.Callback<LoginUser> {
                override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                    if (response.code() == 200) {
                        val user = response.body()
                        user!!.loginDateTime = Date()

                        // Save data login
                        getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE).edit().putString(MY_LOGIN_PREF_KEY, Gson().toJson(user)).apply()

                        if (user.status != "1") {
                            Toast.makeText(this@LoginActivity, "Wrong Username Or Password", Toast.LENGTH_LONG).show()
                            btn_login.visibility = View.VISIBLE
                            progressBar.visibility= View.INVISIBLE
                        } else {
                            //set notif
                            val savedUser = Gson().fromJson<LoginUser>(this@LoginActivity.getSharedPreferences(LoginActivity.MY_LOGIN_PREF, Context.MODE_PRIVATE).getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
                            val id_user = savedUser.id
                            API.notification(id_user).enqueue(object : Callback<ArrayList<Notif>> {
                                override fun onResponse(call: Call<ArrayList<Notif>>?, response: Response<ArrayList<Notif>>?) {
                                    val datas = response?.body()

                                    datas?.forEach {
                                        val id = it.id
                                        val deadline = it.deadline
                                        val nama_dlr = it.namaDlr
                                        val title = it.title
                                        val pi = it.pi
                                        val ca = it.ca

                                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
                                        try {
                                            val dap = inputFormat.parse(deadline + "T07:31:00.000Z")
                                            Log.i("notifNina", " deadline : " + deadline + dap)
                                            val id_pica = "ok"
                                            NotificationScheduler.setAndSaveReminder(this@LoginActivity, title, nama_dlr, id_pica, dap, id)
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                    }

                                    startActivity(Intent(this@LoginActivity, NavigationDrawer::class.java))
                                    finish()
                                }

                                override fun onFailure(call: Call<ArrayList<Notif>>?, t: Throwable?) {

                                }

                            })
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Wrong Username Or Password", Toast.LENGTH_LONG).show()
                        btn_login.visibility = View.VISIBLE
                        progressBar.visibility= View.INVISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_LONG).show()
                    btn_login.visibility = View.VISIBLE
                    progressBar.visibility= View.INVISIBLE
                }
            })
        }
    }

    companion object {

        const val MY_LOGIN_PREF = "myLoginPref"
        const val MY_LOGIN_PREF_KEY = "loginPrefKey"
        val TIME_OUT = 2000

    }
}