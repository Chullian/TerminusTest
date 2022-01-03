package com.chullian.terminustest.di

import com.chullian.terminustest.data.repository.auth.AuthRepository
import com.chullian.terminustest.data.repository.auth.AuthRepositoryImpl
import com.chullian.terminustest.data.repository.tweet.TweetRepository
import com.chullian.terminustest.data.repository.tweet.TweetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by binMammootty on 02/01/2022.
 */
@Module
@InstallIn(SingletonComponent::class)

abstract class TweetModule {
    @Binds
    abstract fun bindTweetRepository(
        tweetRepositoryImpl: TweetRepositoryImpl
    ): TweetRepository

}