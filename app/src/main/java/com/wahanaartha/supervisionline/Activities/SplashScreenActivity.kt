package com.wahanaartha.supervisionline.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF
import com.wahanaartha.supervisionline.Activities.LoginActivity.Companion.MY_LOGIN_PREF_KEY
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            val savedUser = Gson().fromJson(getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE).getString(MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

            if (savedUser != null && savedUser.status == "1" && savedUser.loginDateTime != null) {
                val loginDateTime = savedUser.loginDateTime
                val now = Calendar.getInstance()
                val loginCalendar = Calendar.getInstance()
                loginCalendar.time = loginDateTime
                loginCalendar.add(Calendar.HOUR, -6)

                if (loginCalendar.before(now)) {
                    startActivity(Intent(this@SplashScreenActivity, NavigationDrawer::class.java))
                    finish()
                }
                else {
                    val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }

            }
            else {
                val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, TIME_OUT.toLong())
    }

    companion object {
        val TIME_OUT = 2000
    }
}
