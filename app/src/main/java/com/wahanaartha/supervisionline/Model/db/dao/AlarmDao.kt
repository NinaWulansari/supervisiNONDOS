package com.wahana.wahanamarketingclub.model.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.wahanaartha.supervisionline.Model.db.model.Alarm
import java.util.*

/**
 * Created by wahana on 4/4/18.
 */

@Dao
abstract class AlarmDao : BaseDao<Alarm> {
    @Query("SELECT * FROM Alarm")
    abstract fun getAllAlarm(): LiveData<List<Alarm>>

    @Query("SELECT * FROM Alarm where date >= :today")
    abstract fun getAllAlarmAfter(today: Date): LiveData<List<Alarm>>

    @Query("DELETE FROM Alarm where date < :today")
    abstract fun deleteAlarm(today: Date)

    @Query("DELETE FROM Alarm where reminderId = :reminderId")
    abstract fun deleteAlarm(reminderId: String)

    @Query("SELECT * FROM Alarm where reminderId = :reminderId limit 1")
    abstract fun getDataByReminderId(reminderId: String): LiveData<Alarm>
}