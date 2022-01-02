package com.chullian.terminustest.data.repository

import android.net.Uri
import com.chullian.terminustest.data.Session
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val session: Session
) : AuthRepository {


    override suspend fun authenticate(email: String, pass: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, pass).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    override suspend fun doRegister(email: String, pass: String, uri: Uri, name: String, bio: String) :FirebaseUser?{
        var result: AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
        if(result.user!=null){
            storeProfileData(email,uri,name,bio)
        }
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    private suspend fun storeProfileData(email: String, uri: Uri, name: String, bio: String) {
        fireStore.collection("user")
            .add(mapOf("name" to name,"email" to email,"bio" to bio))
            .await()
    }

    override fun isLoggedIn() = session.isLoggedIn

}