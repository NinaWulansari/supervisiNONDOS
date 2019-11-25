package com.wahanaartha.supervisionline.Model.db.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.wahanaartha.supervisionline.Activities.NavigationDrawer
import com.wahanaartha.supervisionline.Model.db.AppDatabase
import com.wahanaartha.supervisionline.Model.db.model.Alarm
import java.util.*

/**
 * Created by wahana on 4/4/18.
 */
class AlarmRepository(context: Context) {

    private val db = AppDatabase.get(context)

    fun getAlarmAfter(date: Date): LiveData<List<Alarm>> {
        return db.alarmDao().getAllAlarmAfter(date)
    }

    fun getAllAlarm(): LiveData<List<Alarm>> {
        return db.alarmDao().getAllAlarm()
    }

    fun insertAlarm(alarm: Alarm) {
        db.alarmDao().insert(alarm)
    }

    fun deleteAlarm() {
        val oneHourAfter = Calendar.getInstance()
        oneHourAfter.add(Calendar.HOUR, 1)
        db.alarmDao().deleteAlarm(oneHourAfter.time)
    }

    fun deleteAlarmbyId(reminderId: String, onFinish: () -> Unit) {

        NavigationDrawer.BackgroundAsyncTask(object : NavigationDrawer.OnBackgroundAction {
            override fun action() {
                db.alarmDao().deleteAlarm(reminderId)
            }

            override fun onPostExecute() {
                onFinish()
            }

        }).execute()

    }

    fun getAlarmById(reminderId: String): LiveData<Alarm> {
        return db.alarmDao().getDataByReminderId(reminderId)
    }
}