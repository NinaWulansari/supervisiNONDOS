package com.wahanaartha.supervisionline.Model.db.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
class Alarm(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var title: String = "",
        var body: String? = null,
        var id_pica: String? = null,
        var date: Date? = null,
        var reminderCode: Int = Random().nextInt(10000) + 1,
        var reminderId: String? = null // query nya bberdasarkan ini, karena ini yg kamu tau dari API kan? setelah dapet querynya, terus ambil remindercode nya untuk ngapus reminder

) {

    override fun toString(): String {
        return title
    }
}