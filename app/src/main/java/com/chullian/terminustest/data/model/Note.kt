package com.chullian.terminustest.data.model

import android.os.SystemClock
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by binMammootty on 02/01/2022.
 */
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long,
    val title: String = "",
    val note: String = "",
    val createdAt: Long = SystemClock.currentThreadTimeMillis(),
    var updatedAt: Long = SystemClock.currentThreadTimeMillis()
)
