package com.wahanaartha.supervisionline.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log


class DispatcherActivity : Activity() {
    internal var type: String? = null
    internal var current_remark: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityClass: Class<*>

        try {
            val prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
            activityClass = Class.forName(
                    prefs.getString("lastActivity", SplashScreenActivity::class.java.name))
            type = prefs.getString("type_supervisi", "")
            current_remark = prefs.getInt("current_remark", 0)

            Log.d("TYPE_NINA", "$type")
            val sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("type_supervisi", type)
            editor.putInt("current_remark", current_remark)
            editor.commit()
        } catch (ex: ClassNotFoundException) {
            activityClass = SplashScreenActivity::class.java
        }
        startActivity(Intent(this, activityClass))
    }
}
