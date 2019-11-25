package com.wahanaartha.supervisionline.Utils

import android.arch.lifecycle.Observer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.wahanaartha.supervisionline.Activities.NotificationScheduler
import com.wahanaartha.supervisionline.Model.db.model.Alarm
import com.wahanaartha.supervisionline.Model.db.repository.AlarmRepository
import java.util.*

/**
 * Created by lely.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        if ("android.intent.action.BOOT_COMPLETED".equals(intent?.action)) {
            val alarmRepo = AlarmRepository(context)
            val alarms = alarmRepo.getAlarmAfter(Date())
            alarms.observeForever(object : Observer<List<Alarm>> {
                override fun onChanged(t: List<Alarm>?) {
                    t ?: return
                    t.forEach {
                        NotificationScheduler.setReminder(context, it.title, it.body
                                ?: "", it.id_pica ?: "", it.date, it.reminderCode)
                    }
                    alarms.removeObserver(this)
                }

            })
        }
    }
}