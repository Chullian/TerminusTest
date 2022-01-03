package com.chullian.terminustest.data.db

import androidx.room.*
import com.chullian.terminustest.data.model.TweetModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by binMammootty on 02/01/2022.
 */
@Dao
interface TweetDao {


    @Query("SELECT * FROM tweet")
    fun findAll(): Flow<List<TweetModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(obj: TweetModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(obj: List<TweetModel>): List<Long>

    @Update
    suspend fun update(obj: TweetModel)

    @Update
    suspend fun update(obj: List<TweetModel>)

}