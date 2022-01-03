package com.chullian.terminustest.data.repository.tweet

import com.chullian.terminustest.data.model.TweetModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by binMammootty on 02/01/2022.
 */
interface TweetRepository {
    suspend fun sentTweet(tweet:String)
    suspend fun retrieveTweets(): Flow<List<TweetModel>>
    suspend fun updateTweetView(tweet:TweetModel)
}