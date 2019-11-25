package com.wahanaartha.supervisionline.Activities

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.arch.lifecycle.Observer
import android.content.ComponentName
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.wahanaartha.supervisionline.Model.db.model.Alarm
import com.wahanaartha.supervisionline.Model.db.repository.AlarmRepository
import com.wahanaartha.supervisionline.R
import java.util.*

/**
 * Created by lely
 */

class NotificationScheduler {

    companion object {
        val REMINDER_REQUEST_CODE = 100
        val TAG = "NotificationScheduler"
        val TITLE = "title"
        val BODY = "body"
        val ID = "ID"

        fun setAndSaveReminder(context: Context, title: String, body: String, id_pica: String, date: Date?, reminderId: String) {
            val reminderCode = setReminder(context, title, body, id_pica, date)
            reminderCode ?: return
            saveReminder(context, title, body, date, reminderCode, reminderId)
        }

        fun setReminder(context: Context, title: String, body: String, id_pica: String, date: Date?,
                        reminderCode: Int = Random().nextInt(10000) + 1): Int? {
            val calendar = Calendar.getInstance()
            val setcalendar = Calendar.getInstance()
            setcalendar.time = date

            if (calendar.after(setcalendar)) {
                return null
            }
            cancelReminder(context, reminderCode)

            // Enable a receiver
            val receiver = ComponentName(context, AlarmReceiver::class.java)
            val pm = context.packageManager

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)

            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(BODY, body)
            intent.putExtra(ID, id_pica)


            Log.i("CODE_REMINDER", "$reminderCode")
            val pendingIntent = PendingIntent.getBroadcast(context, reminderCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
            am.set(AlarmManager.RTC_WAKEUP, setcalendar.timeInMillis, pendingIntent)

            return reminderCode
        }

        private fun saveReminder(context: Context, title: String, body: String, date: Date?, reminderCode: Int, reminderId: String) {
            val alarmRepo = AlarmRepository(context)

            NavigationDrawer.BackgroundAsyncTask(object : NavigationDrawer.OnBackgroundAction {
                override fun action() {
                    alarmRepo.insertAlarm(Alarm(title = title, body = body, date = date, reminderCode = reminderCode, reminderId = reminderId))
                }

                override fun onPostExecute() {

                }

            }).execute()
        }

        fun deleteAllReminder(context: Context) {
            val alarmRepo = AlarmRepository(context = context)
            NavigationDrawer.BackgroundAsyncTask(object : NavigationDrawer.OnBackgroundAction {
                override fun action() {
                    val allAlarms = alarmRepo.getAllAlarm()
                    allAlarms.observeForever(object : Observer<List<Alarm>> {
                        override fun onChanged(t: List<Alarm>?) {
                            t?.forEach {
                                cancelReminder(context, it.reminderCode)
                            }
                            allAlarms.removeObserver(this)
                        }
                    })
                }

                override fun onPostExecute() {
                }

            }).execute()
        }

        // delete reminder/notifnya dari method ini, tapi ngapus di db nya bikin query lagi kayanya, eh, bikin lagi deh querynya eeeeemmmmm hapusnya beradasarkan id atau code (masih belum ngeh aku ki
        fun cancelReminder(context: Context, reminderCode: Int) {
            val receiver = ComponentName(context, AlarmReceiver::class.java)
            val pm = context.packageManager

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)

            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, reminderCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
            am.cancel(pendingIntent)
            pendingIntent.cancel()

        }

        fun showNotification(context: Context, cls: Class<*>, title: String, content: String) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationIntent = Intent(context, cls)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(cls)
            stackBuilder.addNextIntent(notificationIntent)

            val pendingIntent = stackBuilder.getPendingIntent(Random().nextInt(1000 - 0), PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context)

            val notification = builder.setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent).build()

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(Random().nextInt(1000 - 0), notification)
        }
    }
}