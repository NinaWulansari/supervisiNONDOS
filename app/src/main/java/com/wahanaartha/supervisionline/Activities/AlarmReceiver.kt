package com.wahanaartha.supervisionline.Activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by lely
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let {
            NotificationScheduler.showNotification(it, PicaActivity::class.java,
                    p1?.getStringExtra(NotificationScheduler.TITLE) ?: "",
                    p1?.getStringExtra(NotificationScheduler.BODY) ?: "")
        }
    }
}