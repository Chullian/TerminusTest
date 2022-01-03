package com.chullian.terminustest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chullian.terminustest.data.model.TweetModel


/**
 * Created by binMammootty on 02/01/2022.
 */

@Database(
    entities = arrayOf(
        TweetModel::class
    ),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTweetDao(): TweetDao
    companion object {
        private const val DB_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}
