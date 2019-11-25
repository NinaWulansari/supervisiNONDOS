package com.wahana.wahanamarketingclub.model.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(vararg obj: T)

    @Update
    fun updateUsers(vararg obj: T)

    @Delete
    fun deleteUsers(vararg obj: T)
}