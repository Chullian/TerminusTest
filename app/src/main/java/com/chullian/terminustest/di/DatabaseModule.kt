package com.chullian.terminustest.di

import android.app.Application
import com.chullian.terminustest.data.db.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by binMammootty on 02/01/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) = AppDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideTweetDao(db:AppDatabase) = db.getTweetDao()

}