package com.chullian.terminustest.di

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
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

//    @Singleton
//    @Provides
//    fun provideFirestoreProfile(fireStore:FirebaseFirestore): CollectionReference = fireStore.collection("user")
//
//    @Singleton
//    @Provides
//    fun provideFirestoreNotes(fireStore:FirebaseFirestore) = fireStore.collection("notes")

}