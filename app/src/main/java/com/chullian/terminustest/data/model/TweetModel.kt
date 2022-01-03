package com.chullian.terminustest.data.model

import android.os.SystemClock
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by binMammootty on 02/01/2022.
 */
@Entity(tableName = "tweet")
data class TweetModel(
    @PrimaryKey
    val tweetId: String,
    val tweet: String = "",
    val author: String = "",
    val authorImage: String = "",
    var views: String = "",
    var sync:Boolean = false,
    val createdAt: Long = SystemClock.currentThreadTimeMillis(),
    var updatedAt: Long = SystemClock.currentThreadTimeMillis()
)
