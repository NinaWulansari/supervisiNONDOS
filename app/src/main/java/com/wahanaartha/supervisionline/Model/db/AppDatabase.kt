package com.wahanaartha.supervisionline.Model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.wahana.wahanamarketingclub.model.db.dao.AlarmDao
import com.wahanaartha.supervisionline.Model.db.converters.Converters
import com.wahanaartha.supervisionline.Model.db.model.Alarm


/**
 * Created by wahana on 4/4/18.
 */
@Database(entities = [
    (Alarm::class)], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {
        private val DB_NAME = "supervisi"

        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                        AppDatabase::class.java, DB_NAME).build()
            }
            return instance!!
        }
    }
}